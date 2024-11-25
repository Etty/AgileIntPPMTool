import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
// connect react with redux
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Routes>
            <Route exact path="/dashboard" Component={Dashboard} />
            <Route exact path="/addProject" Component={AddProject} />
            <Route exact path="/updateProject/:id" Component={UpdateProject} />
            <Route exact path="/projectBoard/:id" Component={ProjectBoard} />
            <Route
              exact
              path="/addProjectTask/:id"
              Component={AddProjectTask}
            />
            <Route
              exact
              path="/updateProjectTask/:backlog_id/:pt_id"
              Component={UpdateProjectTask}
            />
          </Routes>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
