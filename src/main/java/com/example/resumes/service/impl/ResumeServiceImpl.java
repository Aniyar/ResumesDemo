package com.example.resumes.service.impl;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.resumes.model.Resume;
import com.example.resumes.repository.ResumeRepository;
import com.example.resumes.service.ResumeService;
import com.example.resumes.util.ImageUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService{
    
    private final ResumeRepository repository;

    public Resume uploadImage(Resume resume, MultipartFile file) throws IOException {
		resume.setImageName(file.getOriginalFilename());
		resume.setImageType(file.getContentType());
		resume.setImageData(ImageUtil.compressImage(file.getBytes()));
		return repository.save(resume);
	}
	
	public byte[] downloadImage(Long resumeId){
        byte[] ans = {};
		Optional<Resume> resume = repository.findById(resumeId);
		if (resume.isPresent()){
			if (resume.get().getImageData() != null){
				return ImageUtil.decompressImage(resume.get().getImageData());
			}
		}
		return ImageUtil.getBytes("src\\main\\resources\\static\\noavatar.jpg");
	}

    public Resume uploadCV(Resume resume, MultipartFile file) throws IOException {
		resume.setFileName(file.getOriginalFilename());
		resume.setFileType(file.getContentType());
		resume.setFileData(ImageUtil.compressImage(file.getBytes()));
		return repository.save(resume);
	}
	
	public byte[] downloadCV(Long resumeId){
        Optional<Resume> resume = repository.findById(resumeId);
		if (resume.isPresent()){
			if (resume.get().getFileData() != null){
				return ImageUtil.decompressImage(resume.get().getFileData());
			}
		}
		return ImageUtil.getBytes("src\\main\\resources\\static\\nocv.pdf");
    }
}
