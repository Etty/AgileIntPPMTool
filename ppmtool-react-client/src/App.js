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
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import { jwtDecode } from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";

// token disappears with page refresh, so we re-set it into app.js
const jwtToken = localStorage.jwtToken;
if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwttoken = jwtDecode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwttoken,
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwttoken.exp < currentTime) {
    window.location.href = "/";
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Routes>
            {/* Public routes */}
            <Route exact path="/" Component={Landing} />
            <Route exact path="/register" Component={Register} />
            <Route exact path="/login" Component={Login} />
            {/* Private routes */}
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
