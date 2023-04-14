package com.example.resumes.resume.service.impl;
import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.resumes.resume.model.Resume;
import com.example.resumes.resume.repository.ResumeRepository;
import com.example.resumes.resume.service.ResumeService;
import com.example.resumes.util.ImageUtil;

import lombok.AllArgsConstructor;

import static com.example.resumes.constants.Constant.PATH_TO_NOJPG;
import static com.example.resumes.constants.Constant.PATH_TO_NOPDF;


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
	
	public byte[] downloadImage(Long resumeId){
        byte[] ans = {};
		Optional<Resume> resume = repository.findById(resumeId);
		if (resume.isPresent()){
			if (resume.get().getImageData() != null){
				return ImageUtil.decompressImage(resume.get().getImageData());
			}
		}
		return ImageUtil.getBytes(PATH_TO_NOJPG);
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
		return ImageUtil.getBytes(PATH_TO_NOPDF);
    }
}
