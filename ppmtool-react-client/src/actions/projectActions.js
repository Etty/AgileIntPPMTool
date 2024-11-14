// axios - library for connection with backend
import axios from "axios";
import { GET_ERRORS, GET_PROJECT, GET_PROJECTS, DELETE_PROJECT } from "./types";

// async - return a Promise
export const createProject = (project, navigate) => async (dispatch) => {
  try {
    // const navigate = useNavigate();
    const res = await axios.post("http://localhost:8080/api/project", project);
    navigate("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    // validation errors from server, which will be displayed on form
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const getProjects = () => async (dispatch) => {
  const res = await axios.get("http://localhost:8080/api/project/all");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data,
  });
};

export const getProject = (id, navigate) => async (dispatch) => {
  try {
    const res = await axios.get(`http://localhost:8080/api/project/${id}`);
    dispatch({
      type: GET_PROJECT,
      payload: res.data,
    });
  } catch (error) {
    navigate("/dashboard");
  }
};

export const deleteProject = (id) => async (dispatch) => {
  await axios.delete(`http://localhost:8080/api/project/${id}`);
  dispatch({
    type: DELETE_PROJECT,
    payload: id,
  });
};
