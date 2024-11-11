// axios - library for connection with backend
import axios from "axios";
import { GET_ERRORS, GET_PROJECTS } from "./types";

// async - return a Promise
export const createProject = (project, navigate) => async (dispatch) => {
  try {
    // const navigate = useNavigate();
    const res = await axios.post("http://localhost:8080/api/project", project);
    navigate("/dashboard");
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
