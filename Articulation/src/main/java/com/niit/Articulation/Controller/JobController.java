
package com.niit.Articulation.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.JobApplicationDao;
import com.niit.Articulation.Dao.JobDao;


import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;
import com.niit.Articulation.Model.UserDetails;




@RestController
public class JobController {
	
	@Autowired
	JobDao jd;
	@Autowired
	Job job;
	@Autowired
	JobApplication jobApplication;
	@Autowired
	JobApplicationDao jobApplicationDao;
	@Autowired
	UserDetails user;
	
//************************************* JOB ***********************************************************************//
//*****************************************************************************************************************//
	
			// Job Status will be New//
	
	
	
//1.create NewJob..............Admin!
		@RequestMapping(value="/createNewJob/", method=RequestMethod.POST)
		public ResponseEntity<Job> createJob(@RequestBody Job job,HttpSession session){
			System.out.println("Create Job");
			
			UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
			
			
			job.setJobDate(new Date());
			job.setStatus("New");
			jd.saveJob(job);
			System.out.println("job" + job.getJobId());
			return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
		
//2.Update Job by Id.............Admin!
		
		@RequestMapping(value="/updateJob/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Job> updatejob(@PathVariable("id") int id, @RequestBody Job job){
			if(jd.list()==null){
				job =new Job();
				job.setErrorMessage("Job does not exist with id : " +job.getJobId());
				return new ResponseEntity<Job>(job, HttpStatus.NO_CONTENT);
			}
			jd.update(job);
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
//3.Delete Job by Id..................Admin!
		
		@RequestMapping(value="/deleteJob/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Job>deleteJob(@PathVariable("id")int id){
			Job job=jd.getJobByJobId(id);
			if(job == null){
				job = new Job();
				job.setErrorMessage("Job does not exist with id : " + id);
				return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
				
			}
			jd.delete(job);
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		
//4.check job by {id}............!
		
		@RequestMapping(value= "/jobById/{id}",method=RequestMethod.GET)
		public ResponseEntity<Job>getJobByJobId(@PathVariable("id")int id){
			Job job = jd.getJobByJobId(id);
			if (job == null){
				job = new Job();
				job.setErrorMessage("Job does not exist with id : " + id);
					return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
					
			}
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		
//5.Show All joblist............!
	@RequestMapping(value="/joblist", method=RequestMethod.GET)
	public ResponseEntity<List<Job>> list(){
		List<Job>list=jd.list();
		if(list.isEmpty()){
			return new ResponseEntity<List<Job>>(list, HttpStatus.NO_CONTENT);
		}
		System.out.println(list.size());
		System.out.println("retrieving job application ");
		return new ResponseEntity<List<Job>>(list, HttpStatus.OK);

}
	
//7.Show JobApplication by UserId and JobId................!
	
	@RequestMapping(value= "/JobByUserIdAndJobId/",method=RequestMethod.GET)
	public ResponseEntity<JobApplication> getJobByJobIdUserID(@RequestBody JobApplication jobApplication, HttpSession session){
		jobApplication = jd.get(jobApplication.getUserid(), jobApplication.getJobid());
		if (jobApplication == null){
			
			jobApplication.setErrorMessage("Job Application does not exist with id : " + jobApplication.getUserid() );
				
				
		}
		return new ResponseEntity<JobApplication >(jobApplication, HttpStatus.OK);
	}

	

	


// ListVacantJobs....................!			
		
@RequestMapping(value="/vacantJobs", method=RequestMethod.GET)
	
	public ResponseEntity<List<Job>> listVacantJobs(){
		List<Job> listVacantJobs=jd.listVacantJobs();
		if(listVacantJobs.isEmpty()){
			return new ResponseEntity<List<Job>>(listVacantJobs,HttpStatus.NO_CONTENT);
		}
		System.out.println(listVacantJobs.size());
		System.out.println("retrieving vacant job");
		return new ResponseEntity<List<Job>>(listVacantJobs,HttpStatus.OK);
	}		
			
//************************* Job Application************************************************************************//
//*****************************************************************************************************************//			
			//JobApplication Status will be Selected or Pending Or Rejected//
	
//1.Create NewJobApply............!
	
		@RequestMapping(value="/NewJobApply/{id}", method=RequestMethod.POST)
		public ResponseEntity<JobApplication> createJobApplication(@RequestBody JobApplication job,HttpSession session,@PathVariable("id")int id){
			System.out.println("Create JobApply");
			
			UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
			job.setStatus("Pending");
			job.setUserid(user.getUserId());
			job.setJobid(id);
			job.setRemarks("no");
			jobApplicationDao.saveJobApplication( job);
			System.out.println("job" + job.getJobApplicationId());
			return new ResponseEntity<JobApplication>(job,HttpStatus.OK);
	}
		
//2.Update JobApplication by Id..............!
		
		@RequestMapping(value="/updateJobApplication/{id}", method=RequestMethod.PUT)
		public ResponseEntity<JobApplication> updateJobApplication(@PathVariable("id") int id, @RequestBody JobApplication jobApply){
			if(jobApplicationDao.listJobApplications()==null){
				jobApply =new JobApplication();
				jobApply.setErrorMessage("JobApplication does not exist with id : " +jobApply.getJobApplicationId());
				return new ResponseEntity<JobApplication>(jobApply, HttpStatus.NO_CONTENT);
			}
			jobApplicationDao.updateJobApplication(jobApply);
			return new ResponseEntity<JobApplication>(jobApply, HttpStatus.OK);
		}
		
//3.check jobApply by {id}..................!
		
				@RequestMapping(value= "/jobApplyById/{id}",method=RequestMethod.GET)
				public ResponseEntity<JobApplication>getJobApplicationByJobApplicationId(@PathVariable("id")int id){
					JobApplication jobApply = jobApplicationDao.getJobApplicationByJobApplicationId(id);
					if (jobApply == null){
						jobApply = new JobApplication();
						jobApply.setErrorMessage("JobApplication does not exist with id : " + id);
							return new ResponseEntity<JobApplication>(jobApply, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<JobApplication>(jobApply, HttpStatus.OK);
				}
				
//4.Show jobApplicationList  By jobId.............!
				
				@RequestMapping(value= "/JobApplicationByJobId/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<JobApplication>>getjobApplicationsByJobId(@PathVariable("id") int id){
					List<JobApplication> jobApplications=jobApplicationDao.jobApplicationsByJobId(id);
					if (jobApplications == null){
						jobApplication.setErrorMessage("Job Application does not exist with jobId : " + id);
							return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
				}
	
//5.Show All jobApplylist............!
			@RequestMapping(value="/jobApplylist", method=RequestMethod.GET)
			public ResponseEntity<List<JobApplication>> listJobApplications(){
				List<JobApplication>list=jobApplicationDao.listJobApplications();
				if(list.isEmpty()){
					return new ResponseEntity<List<JobApplication>>(list, HttpStatus.NO_CONTENT);
				}
				System.out.println(list.size());
				System.out.println("retrieving jobs ");
				return new ResponseEntity<List<JobApplication>>(list, HttpStatus.OK);

		}
	
				
//6.Show pending jobApplication By userId.........!
				
				@RequestMapping(value= "/JobApplicationByUserId/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<JobApplication>>getJobApplicationsByUserId(@PathVariable("id") String id){
					List<JobApplication> jobApplications=jobApplicationDao.jobApplicationsByUserId(id);
					if (jobApplications == null){
						jobApplication.setErrorMessage("Job Application does not exist with userId : " + id);
							return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
				}
				
//9.Show Selected jobApplication By userId............!
				
				@RequestMapping(value= "/selectedJobApplicationById/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<JobApplication>>getselectedjobApplicationsByUserId(@PathVariable("id") String id){
					List<JobApplication> jobApplications=jobApplicationDao.selectedjobApplicationsByUserId(id);
					if (jobApplications == null){
						jobApplication.setErrorMessage("Job Application does not exist with userId : " + id);
							return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
				}
//10.Show Rejected jobApplication By userId..................!
				
				@RequestMapping(value= "/rejectedJobApplicationById/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<JobApplication>>getrejectedjobApplicationsByUserId(@PathVariable("id") String id){
					List<JobApplication> jobApplications=jobApplicationDao.rejectedjobApplicationsByUserId(id);
					if (jobApplications == null){
						jobApplication.setErrorMessage("Job Application does not exist with userId : " + id);
							return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
				}
				
//11.Show InterviewCall jobApplication By userId...............!
				@RequestMapping(value= "/InterviewCallById/{id}",method=RequestMethod.GET)
				public ResponseEntity<List<JobApplication>>getcallForInterviewjobApplicationsByUserId(@PathVariable("id") String id){
					List<JobApplication> jobApplications=jobApplicationDao.callForInterviewjobApplicationsByUserId(id);
					if (jobApplications == null){
						jobApplication.setErrorMessage("Job Application does not exist with userId : " + id);
							return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.NOT_FOUND);
							
					}
					return new ResponseEntity<List<JobApplication>>(jobApplications, HttpStatus.OK);
				}
				
//12. JobApplication Pending by Admin.........!
				
				@RequestMapping(value="/pendingJobApplication/{id}", method=RequestMethod.PUT)
				public ResponseEntity<JobApplication>pendingJobApplication(@PathVariable("id") int id,HttpSession session)
				{	
					
					JobApplication job=jobApplicationDao.getJobApplicationByJobApplicationId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(job!=null))
						{
							job.setStatus("Pending");
							jobApplicationDao.updateJobApplication(job);
									return new ResponseEntity<JobApplication>(job,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<JobApplication>(job,HttpStatus.NOT_FOUND);
								}
				}
				
//13. JobApplication Selected by Admin..........!
				
				@RequestMapping(value="/selectJobApplication/{id}", method=RequestMethod.PUT)
				public ResponseEntity<JobApplication>selectJobApplication(@PathVariable("id") int id,HttpSession session)
				{	
					
					JobApplication job=jobApplicationDao.getJobApplicationByJobApplicationId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(job!=null))
						{
							job.setStatus("InterviewCall");
							jobApplicationDao.updateJobApplication(job);
									return new ResponseEntity<JobApplication>(job,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<JobApplication>(job,HttpStatus.NOT_FOUND);
								}
				}
				
//13 JobApplication Rejected by Admin..........!
				
				@RequestMapping(value="/rejectJobApplication/{id}", method=RequestMethod.PUT)
				public ResponseEntity<JobApplication>rejectJobApplication(@PathVariable("id") int id,HttpSession session)
				{	
					
					JobApplication job=jobApplicationDao.getJobApplicationByJobApplicationId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(job!=null))
						{
							job.setStatus("Reject");
							jobApplicationDao.updateJobApplication(job);
									return new ResponseEntity<JobApplication>(job,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<JobApplication>(job,HttpStatus.NOT_FOUND);
								}
				}
				
//14.InterviewCall JobApplication  by Admin..........!
				
				@RequestMapping(value="/interviewCallForUser/{id}", method=RequestMethod.PUT)
				public ResponseEntity<JobApplication>interviewJobApplication(@PathVariable("id") int id,HttpSession session)
				{	
					
					JobApplication job=jobApplicationDao.getJobApplicationByJobApplicationId(id);
						if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
								(job!=null))
						{
							job.setStatus("InterviewCall");
							jobApplicationDao.updateJobApplication(job);
									return new ResponseEntity<JobApplication>(job,HttpStatus.OK);
								}
						else
						{
									
									return new ResponseEntity<JobApplication>(job,HttpStatus.NOT_FOUND);
								}
				}
	//get my applied job..............!
				@RequestMapping(value= "/appliedJobs",method=RequestMethod.GET)
				public ResponseEntity<List<JobApplication>>getAppliedJobs(HttpSession session){
				UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
				return new ResponseEntity<List<JobApplication>>(jobApplicationDao.jobApplicationsByUserId(user.getUserId()),HttpStatus.OK);
				}		
					
}
				
				


