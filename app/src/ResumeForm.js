import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';


const ResumeForm = () => {
  const initialFormState = {
    username: '',
    position: '',
    salary: 0,
    company: '',
    comment: ''
  };

  const [selectedFile, setSelectedFile] = useState(false);

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };


  const [resume, setResume] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id !== 'new') {
      fetch(`/resumes/${id}`)
        .then(response => response.json())
        .then(data => setResume(data));
    }
  }, [id, setResume]);

  const handleChange = (event) => {
    const { name, value } = event.target

    setResume({ ...resume, [name]: value })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    await fetch(`/resumes/${resume.id ? `/${resume.id}` : 'new'}`, {
      method: (resume.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(resume)
    });

    if (resume.id){
      const formData = new FormData();
      formData.append('CV', selectedFile);
      fetch(`/resumes/${id}/upload/CV`, {
        method: 'PUT',
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => {
        })
        .catch((error) => {
        });
    }
    setResume(initialFormState);
    navigate('/resumes');
  }

  const title = <h2>{resume.id ? 'Edit resume' : 'Add resume'}</h2>;

  return (<div>
      <Container>
        {title}
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="username">Username</Label>
            <Input type="text" name="username" id="username" value={resume.username || ''}
                   onChange={handleChange} autoComplete="username"/>
          </FormGroup>
          <FormGroup>
            <Label for="position">Position</Label>
            <Input type="text" name="position" id="position" value={resume.position || ''}
                   onChange={handleChange} autoComplete="position"/>
          </FormGroup>
          <FormGroup>
            <Label for="salary">Salary</Label>
            <Input type="number" name="salary" id="salary" value={resume.salary || ''}
                   onChange={handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="company">Company</Label>
            <Input type="text" name="company" id="company" value={resume.company || ''}
                   onChange={handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="comment">Comment</Label>
            <Input type="text" name="comment" id="comment" value={resume.comment || ''}
                   onChange={handleChange} />
          </FormGroup>
          {resume.id &&
          <FormGroup>
            <Label for="CV">CV</Label>
            <Input type="file" onChange={handleFileChange} />
          </FormGroup>
          }
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/resumes">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  )
};

export default ResumeForm;