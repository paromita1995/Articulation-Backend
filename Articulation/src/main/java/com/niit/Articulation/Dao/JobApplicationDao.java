package com.niit.Articulation.Dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;


@Repository
public interface JobApplicationDao {
	
	public boolean saveJobApplication(JobApplication jobApplication);//1............!
	
	public boolean updateJobApplication(JobApplication jobApplication);//2............!
	
	public JobApplication getJobApplicationByJobApplicationId(int jobApplicationId);//3............!
	
	public List<JobApplication> jobApplicationsByJobId(int jobId);//4...................!
	
	public List<JobApplication> listJobApplications();//5.................!
	
	public List<JobApplication> jobApplicationsByUserId(String userId);//6....................!
	
	public boolean isJobExist(String userId, int jobId);//7.........................!pending//
	
	public List<JobApplication> getMyAppliedJobs(String userid);//8......................!same as JobApplication function (6)
	
	public List<JobApplication> selectedjobApplicationsByUserId(String userId);//9...................!
	
	public List<JobApplication> rejectedjobApplicationsByUserId(String userId);//10...................!
	
	public List<JobApplication> callForInterviewjobApplicationsByUserId(String userId);//11.............!

}
