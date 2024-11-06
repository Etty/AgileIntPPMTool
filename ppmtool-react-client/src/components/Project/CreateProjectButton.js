import React from "react";
import { Link } from "react-router-dom";

// functional component. It's stateless comparing to class-based component. It can access props only
// functional components are simple JavaScript functions that take props as input and return JSX elements
const CreateProjectButton = () => {
  return (
    <React.Fragment>
      <Link to="/addProject" className="btn btn-lg btn-info">
        Create a Project
      </Link>
    </React.Fragment>
  );
};

export default CreateProjectButton;
