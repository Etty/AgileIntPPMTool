import React, { Component } from "react";
import { Link } from "react-router-dom";
import withRouter from "../withRouter";
import Backlog from "./Backlog";

class ProjectBoard extends Component {
  render() {
    const { id } = this.props.router.params;
    return (
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        <Backlog />
      </div>
    );
  }
}

export default withRouter(ProjectBoard);
