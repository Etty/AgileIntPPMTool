package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.BacklogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Iterator;
import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskSRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
//        task must be assigned to some project
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        if (backlog == null) {
            throw new ProjectIdException("Project ID '" + projectIdentifier + "' not found");
        }

        projectTask.setBacklog(backlog);

        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;

        backlog.setPTSequence(BacklogSequence);

//        Add sequence to project task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

//        setting priority to low when it's not specified
        if (projectTask.getPriority() == null) { //in the future we need projectTask.getPriority() == null to handle the form
            projectTask.setPriority(3);
        }

        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskSRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String backlogId) {
        Project project = projectRepository.findByProjectIdentifier(backlogId);
        if (project == null) {
            throw new ProjectIdException("Project with ID:'" + backlogId + "' does not exist");
        }
        return projectTaskSRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectIdException("Project with ID:'" + backlog_id + "' does not exist");
        }
        ProjectTask projectTask = projectTaskSRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectIdException("Project task '" + pt_id + "' does not exist");
        }
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectIdException("Project task '" + pt_id + "' does not exist in project: '" + backlog_id + "'");
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTask = updatedTask;
        return projectTaskSRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        Backlog backlog = projectTask.getBacklog();
        List<ProjectTask> pts = backlog.getProjectTasks();
        pts.remove(projectTask);
        backlogRepository.save(backlog);
        
        projectTaskSRepository.delete(projectTask);
    }
}
