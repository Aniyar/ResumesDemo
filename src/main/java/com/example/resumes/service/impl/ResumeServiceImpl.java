package com.example.resumes.service.impl;

import com.example.resumes.exceptionHandler.*;
import com.example.resumes.model.Resume;
import com.example.resumes.repository.resumeRepository.ResumeRepository;
import com.example.resumes.service.ResumeService;
import com.example.resumes.util.ContentUtils;
import com.example.resumes.util.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    
    private final ResumeRepository repository;

    public Resume uploadImage(Long resumeId, MultipartFile file) throws IOException {
		Optional<Resume> optional = repository.findById(resumeId);
		if (optional.isPresent()){
			Resume resume = optional.get();
			resume.setImageName(file.getOriginalFilename());
			resume.setImageType(file.getContentType());
			resume.setImageData(ImageUtil.compressImage(file.getBytes()));
			repository.save(resume);
			return resume;
		}
		return new Resume();
	}

	public ResponseEntity downloadImage(Long resumeId) throws ResumeNotFoundException, ImageNotFoundException {
        byte[] ans = {};
		Optional<Resume> resume = repository.findById(resumeId);
		if (resume.isPresent()){
			if (resume.get().getImageData() != null){
				byte[] imgData = ImageUtil.decompressImage(resume.get().getImageData());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_PNG);
				headers.setContentDisposition(ContentDisposition.inline().build());
				return new ResponseEntity(imgData, headers, HttpStatus.OK);
			}
			throw new ImageNotFoundException(resumeId);
		}
		throw new ResumeNotFoundException(resumeId);
	}

    public ResponseEntity uploadCV(Long resumeId, MultipartFile file) throws IOException, ResumeNotFoundException, FileUploadException {
		Optional<Resume> optional = repository.findById(resumeId);
		if (optional.isPresent()){
			Resume resume = optional.get();
			try {
				resume.setFileName(file.getOriginalFilename());
				resume.setFileType(file.getContentType());
				resume.setFileData(ImageUtil.compressImage(file.getBytes()));
				repository.save(resume);
			}catch(Exception e){
				throw new FileUploadException();
			}
			finally {
				return new ResponseEntity<>(resume, HttpStatus.OK);
			}
		}
		throw new ResumeNotFoundException(resumeId);
	}

	public ResponseEntity downloadCV(Long resumeId) throws CVNotFoundException, ResumeNotFoundException {
        Optional<Resume> resume = repository.findById(resumeId);
		if (resume.isPresent()){
			if (resume.get().getFileData() != null){
				byte[] pdfContent = ImageUtil.decompressImage(resume.get().getFileData());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDisposition(ContentDisposition.inline().build());
				return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
			}
			throw new CVNotFoundException(resume.get().getUsername());
		}
		throw new ResumeNotFoundException(resumeId);
    }

	public ResponseEntity<Resume> putResume(Long id, Resume resume) throws ContentNotAllowedException, ResumeNotFoundException, NullFieldNotAllowedException {
		if (repository.existsById(id)){
			if (resume.getComment() != null){
				List<ObjectError> errors = ContentUtils.getContentErrorsFrom(resume.getComment());
				if (!errors.isEmpty()){
					throw new ContentNotAllowedException(errors);
				}
			}
			if (resume.getSalary() == null) throw new NullFieldNotAllowedException("salary");
			if (resume.getUsername() == null) throw new NullFieldNotAllowedException("username");
			if (resume.getPosition() == null) throw new NullFieldNotAllowedException("position");
			return new ResponseEntity<>(repository.save(resume), HttpStatus.OK);
		}
		else{ throw new ResumeNotFoundException(id);}
	}

	public ResponseEntity<Resume> getResumeByID(Long id) throws ResumeNotFoundException {
		if (repository.existsById(id)){
			return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
		}else{
			throw new ResumeNotFoundException(id);
		}
	}


}
