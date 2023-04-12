import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';


const ImageEdit = () => {

  const navigate = useNavigate();
  const { id } = useParams();
  const [selectedFile, setSelectedFile] = useState(false);

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append('resumeImage', selectedFile);

    fetch(`/resumes/${id}/upload/image`, {
    method: 'PUT',
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      // Handle the response from the server
    })
    .catch((error) => {
      // Handle errors
    });
    navigate('/resumes');
  }

  const title = <h2>{'Edit Avatar'}</h2>;
  //const title = 'Edit Avatar';
  return (<div>
      <Container>
        {title}
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="resumeImage">Image</Label>
            <Input type="file" onChange={handleFileChange} />
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/resumes">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  );
};

export default ImageEdit;