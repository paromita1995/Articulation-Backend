package com.niit.Articulation.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.ForumDao;
import com.niit.Articulation.Dao.JobDao;
import com.niit.Articulation.Model.Blog;
import com.niit.Articulation.Model.Forum;
import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;
import com.niit.Articulation.Model.UserDetails;

@Repository("jobDao")
@Transactional
public class JobDaoImpl  implements JobDao {
private SessionFactory sessionFactory;
	
	public JobDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public boolean saveJob(Job job) {
		try
		{
			Session session = getSession();

			session.save(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean update(Job job) {
		try
		{
			Session session = getSession();

			session.update(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
		
	}

	public boolean delete(Job job) {
		try
		{
			Session session = getSession();

			session.delete(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public Job getJobByJobId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM Job p where jobId=:jobId");
		query.setParameter("jobId",id);
		Job  p=(Job)query.uniqueResult();
		session.close();
		return p;
	}

	public List<Job> list() {
		Session session = getSession();
		Query query=session.createQuery("from Job");
		List<Job> list=query.list();
		session.close();
		return list;
	}

	public List<Job> getMyAppliedJobs(String userid) {
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

	public JobApplication get(String userid, int jobid) {
		Session session=getSession();
		Query query=session.createQuery("FROM JobApplication p where userid=:userid and jobid=:jobid");
		query.setParameter("userid", userid);
		query.setParameter("jobid", jobid);
		JobApplication p=(JobApplication)query.uniqueResult();
		session.close();
		return p;
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

	public boolean applyForJob(JobApplication jobApplication) {
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

	public List<JobApplication> listJobApplications() {
		Session session = getSession();
		Query query=session.createQuery("from JobApplication");
		List<JobApplication> jobApplicationList=query.list();
		session.close();
		return jobApplicationList;
	}

	public List<Job> listVacantJobs() {
		Session session = getSession();
		Query query=session.createQuery("from Job where status=:status");
		query.setParameter("status", "Open" );
		List<Job> jobApplicationList=query.list();
		session.close();
		return jobApplicationList;
	}


}
