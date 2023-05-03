// App.js
import React, { useState, useEffect } from 'react';
import ResumeList from './ResumeList';
import ResumeForm from './ResumeForm';

import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ImageEdit from './ImageEdit';
import JobList from "./JobList";

const App = () => {
  return (
    <Router>
      <Routes>
      <Route path='/resumes/:id' element={<ResumeForm/>}/>
      <Route path='/resumes/:id/image' element={<ImageEdit/>}/>
      <Route path='/resumes' exact={true} element={<ResumeList/>}/>
      <Route path='/jobs' exact={true} element={<JobList/>}/>
      </Routes>
    </Router>
  )
}

export default App;