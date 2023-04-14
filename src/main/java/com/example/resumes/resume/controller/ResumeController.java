package com.example.resumes.resume.controller;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.resumes.resume.model.Resume;
import com.example.resumes.resume.repository.ResumeRepository;
import com.example.resumes.resume.service.impl.ResumeServiceImpl;
import com.example.resumes.util.ImageUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    Optional<Resume> getResumeById(@PathVariable Long id){
        return resumeRepository.findById(id);
    }

    @GetMapping("/{resumeId}/download/image")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long resumeId) throws IOException{
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(resumeService.downloadImage(resumeId));
    }
//javax
    @GetMapping("/{resumeId}/download/CV")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long resumeId){
        byte[] pdfContent = resumeService.downloadCV(resumeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().build());
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
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
    ResponseEntity<Resume> uploadCV(@PathVariable Long resumeId, @RequestParam("CV")MultipartFile file) throws IOException{
        Optional<Resume> resume = resumeRepository.findById(resumeId);

        return resume.isPresent() ?
                new ResponseEntity<>(resumeService.uploadCV(resume.get(), file), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Resume> putResume(@PathVariable Long id,
                                     @RequestBody Resume resume) {
        return (resumeRepository.existsById(id) ?
                new ResponseEntity<>(resumeRepository.save(resume), HttpStatus.OK) :
                new ResponseEntity<>(resumeRepository.save(resume), HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    void deleteResume(@PathVariable Long id){
        resumeRepository.deleteById(id);
    }
}
