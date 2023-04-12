import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link, useNavigate, useParams } from 'react-router-dom';


function ByteArrayToImage({resumeId}) {
  const [imageSrc, setImageSrc] = React.useState("");
  const imageUrl = `resumes/${resumeId}/download/image`;
  const navigate = useNavigate();
  console.log(imageUrl);
  React.useEffect(() => {
    async function fetchImage() {

      const response = await fetch(imageUrl);
      const blob = await response.blob();
      const url = URL.createObjectURL(blob);
      setImageSrc(url);
    }
    fetchImage();
  }, [imageUrl]);

  return <img src={imageSrc} alt="img" width="40px" onClick={() => navigate(`/resumes/${resumeId}/image`)}/>;
}


const ResumeList = () => {

  const [resumes, setResumes] = useState([]);
  const [loading, setLoading] = useState(false);
  


  useEffect(() => {
    setLoading(true);

    fetch('resumes')
      .then(response => response.json())
      .then(data => {
        setResumes(data);
        setLoading(false);
      })
  }, []);

  const remove = async (id) => {
    await fetch(`http://localhost:8080/resumes/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedResumes = [...resumes].filter(i => i.id !== id);
      setResumes(updatedResumes);
    });
  }

  if (loading) {
    return <p>Loading...</p>;
  }

  const resumeList = resumes.map(resume => {
    return <tr key={resume.id}>
      <td>
        <ByteArrayToImage resumeId={resume.id}/>
      </td>
      <td style={{whiteSpace: 'nowrap'}}>{resume.username}</td>
      <td>{resume.position}</td>
      <td>{resume.salary}</td>
      <td>{resume.company}</td>
      <td>{resume.comment}</td>
      
      {/* <td><Button_CV resumeId={resume.id}/></td> */}
      <td><a href={`http://localhost:8080/resumes/${resume.id}/download/CV`} without rel="noopener noreferrer" target="_blank">
      <button trailingIcon="picture_as_pdf" label="Resume">
        PDF
      </button>
   </a></td>
      <td>
        <ButtonGroup>
          <Button size="sm" color="primary" tag={Link} to={"/resumes/" + resume.id}>Edit</Button>
          
          <Button size="sm" color="danger" onClick={() => remove(resume.id)}>Delete</Button>
        </ButtonGroup>
      </td>
    </tr>
  });

  return (
    <div>
      <Container fluid>
        <div className="float-end">
          <Button color="success" tag={Link} to="/resumes/new">Add Resume</Button>
        </div>
        <h3>Resumes</h3>
        <Table className="mt-4">
          <thead>
          <tr>
            <th width="10%">Image</th>
            <th width="10%">Name</th>
            <th width="10%">Position</th>
            <th width="10%">Salary</th>
            <th width="10%">Company</th>
            <th width="10%">Comment</th>
            <th width="10%">CV</th>
            <th width="10%">Actions</th>
          </tr>
          </thead>
          <tbody>
          {resumeList}
          </tbody>
        </Table>
      </Container>
    </div>
  );
};

export default ResumeList;