import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link, useNavigate, useParams } from 'react-router-dom';


const JobList = () => {

  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(false);
  


  useEffect(() => {
    setLoading(true);

    fetch('jobs')
      .then(response => response.json())
      .then(data => {
        setJobs(data);
        setLoading(false);
      })
  }, []);


  if (loading) {
    return <p>Loading...</p>;
  }

  const jobList = jobs.map(job => {
    return <tr key={job.id}>
      <td>{job.position}</td>
      <td>{job.category}</td>
      <td>{job.company}</td>
      <td>{job.salary}</td>
      <td>{job.location}</td>
      <td>{job.comment}</td>
    </tr>
  });

  return (
    <div>
      <Container fluid>
        <h3>Jobs</h3>
        <Table className="mt-4">
          <thead>
          <tr>
            <th width="5%">Position</th>
            <th width="10%">Category</th>
            <th width="10%">Company</th>
            <th width="10%">Salary</th>
            <th width="10%">Location</th>
            <th width="55%">Comment</th>
          </tr>
          </thead>
          <tbody>
          {jobList}
          </tbody>
        </Table>
      </Container>
    </div>
  );
};

export default JobList;