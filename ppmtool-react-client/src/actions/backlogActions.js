import axios from "axios";
import { GET_ERRORS } from "./types";

export const addProjectTask =
  (backlog_id, project_task, navigate) => async (dispatch) => {
    try {
      await axios.post(`/api/backlog/${backlog_id}`, project_task);
      navigate(`/projectBoard/${backlog_id}`);
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
