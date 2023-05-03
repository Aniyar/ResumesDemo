package com.example.resumes.controller;

import com.example.resumes.exceptionHandler.*;
import com.example.resumes.model.Resume;
import com.example.resumes.repository.resumeRepository.ResumeRepository;
import com.example.resumes.service.impl.ResumeServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/resumes")
@AllArgsConstructor
public class ResumeController {
    private final ResumeRepository resumeRepository;
    private final ResumeServiceImpl resumeService;

    static Logger logger = LoggerFactory.getLogger(ResumeController.class);

    @GetMapping()
    Iterable<Resume> getResumes(){
        return resumeRepository.findAll();
    }
    
    @GetMapping("/{id}")
    ResponseEntity<Resume> getResumeById(@PathVariable Long id) throws ResumeNotFoundException {
        return resumeService.getResumeByID(id);
    }

    @GetMapping("/{resumeId}/download/image")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long resumeId) throws IOException, ResumeNotFoundException, ImageNotFoundException {
        return resumeService.downloadImage(resumeId);
    }
//javax
    @GetMapping("/{resumeId}/download/CV")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long resumeId) throws ResumeNotFoundException, CVNotFoundException {
        return resumeService.downloadCV(resumeId);
    }


    @PostMapping("/new")
    Resume postResume(@RequestBody Resume resume){
        return resumeRepository.save(resume);
    }


    @PutMapping("/{resumeId}/upload/image")
    Resume uploadImage(@PathVariable Long resumeId, @RequestParam("resumeImage")MultipartFile file) throws IOException{
        return resumeService.uploadImage(resumeId, file);
    }

    @PutMapping("/{resumeId}/upload/CV")
    ResponseEntity<Resume> uploadCV(@PathVariable Long resumeId, @RequestParam("CV")MultipartFile file) throws ResumeNotFoundException, IOException, FileUploadException {
        return resumeService.uploadCV(resumeId, file);
    }

    @PutMapping("/{id}")
    ResponseEntity<Resume> putResume(@PathVariable Long id,
                                     @RequestBody Resume resume) throws ResumeNotFoundException, ContentNotAllowedException, NullFieldNotAllowedException {
        return resumeService.putResume(id, resume);
    }

    @DeleteMapping("/{id}")
    void deleteResume(@PathVariable Long id){
        resumeRepository.deleteById(id);
    }
}
