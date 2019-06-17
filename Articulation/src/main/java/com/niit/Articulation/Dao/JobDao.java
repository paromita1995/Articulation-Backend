package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;






public interface JobDao {
	   public boolean saveJob(Job job);//1..............!
		
		public boolean update(Job job);//2..............!
		
	
		
		public boolean delete(Job job);//3..............!
		
		public Job getJobByJobId(int id);//4..............!
		
		public List<Job> list();//5..............!
		
		public List<Job> getMyAppliedJobs(String userid);//6..............!//same as JobApplication Function No(6)
		
		public JobApplication get(String userid, int jobid);//7..............!
		
		public boolean updateJobApplication(JobApplication jobApplication);//same as JobApplication Function No (2)
		
		public boolean applyForJob(JobApplication jobApplication);//same as JobApplication Function No (1)
		
		public List<JobApplication> listJobApplications();//same as JobApplication Function No (5)
		
		public List<Job> listVacantJobs();//11..............!//pending
		
	}
