package com.niit.Articulation.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.JobApplicationDao;
import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;



@Repository("jobApplicationDao")
@Transactional
public class JobApplicationDaoImpl implements JobApplicationDao {
	@Autowired
private SessionFactory sessionFactory;
	
	public JobApplicationDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public boolean saveJobApplication(JobApplication jobApplication) {
		try
		{
			Session session = getSession();

			session.save(jobApplication);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean updateJobApplication(JobApplication jobApplication) {
		try
		{
			Session session = getSession();

			session.update(jobApplication);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public JobApplication getJobApplicationByJobApplicationId(int jobApplicationId) {
		Session session = getSession();
		Query query=session.createQuery("FROM JobApplication p where jobApplicationId=:jobApplicationId");
		query.setParameter("jobApplicationId",jobApplicationId);
		JobApplication  p=(JobApplication)query.uniqueResult();
		session.close();
		return p;
	}

	public List<JobApplication> jobApplicationsByJobId(int jobId) {
		 Session session=getSession();
			
			try{
				Query query=session.createQuery("from JobApplication where jobId = ?");
				query.setInteger(0, jobId);
				
				return query.list();
				
			}catch(HibernateException e){
				e.printStackTrace();
				return null;
			}
	}

	public List<JobApplication> listJobApplications() {
		Session session = getSession();
		Query query=session.createQuery("from JobApplication");
		List<JobApplication> jobApplicationList=query.list();
		session.close();
		return jobApplicationList;
	}

	public List<JobApplication> jobApplicationsByUserId(String userId) {
Session session=getSession();
		
try{
	Query query=session.createQuery("from JobApplication where userId= ? and Status= ?");
	query.setString(0, userId);
	query.setString(1, "Pending");
	
	return query.list();
	
}catch(HibernateException e){
	e.printStackTrace();
	return null;
}
	}

	public boolean isJobExist(String userId, int jobId) {
Session session=getSession();
try{
	Query query=session.createQuery("from JobApplication where userId = ? and jobId = ?");
	query.setString(0, userId);
	query.setInteger(1, jobId);
	
	return !query.list().isEmpty();
	
}catch(HibernateException e){
	e.printStackTrace();
	return false;
}

	
	}

	public List<JobApplication> getMyAppliedJobs(String userid) {
Session session=getSession();
try{
	Query query=session.createQuery("from JobApplication where userId = ?");
	query.setString(0, userid);
	
	return query.list();
	
}catch(HibernateException e){
	e.printStackTrace();
	return null;
}
	}

	public List<JobApplication> selectedjobApplicationsByUserId(String userId) {
		Session session=getSession();
		
		try{
			Query query=session.createQuery("from JobApplication where userId= ? and Status= ?");
			query.setString(0, userId);
			query.setString(1, "Select");
			
			return query.list();
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	public List<JobApplication> rejectedjobApplicationsByUserId(String userId) {
Session session=getSession();
		
		try{
			Query query=session.createQuery("from JobApplication where userId= ? and Status= ?");
			query.setString(0, userId);
			query.setString(1, "Reject");
			
			return query.list();
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	public List<JobApplication> callForInterviewjobApplicationsByUserId(String userId) {
Session session=getSession();
		
		try{
			Query query=session.createQuery("from JobApplication where userId= ? and Status= ?");
			query.setString(0, userId);
			query.setString(1, "InterviewCall");
			
			return query.list();
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}
}
	
	

	


