package com.pagination.service;

import java.util.*;

import com.pagination.entities.Photo;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;

@Repository
@SuppressWarnings("unchecked")
public class PhotoService {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public HashMap<String, Long> getPhotosByOwner()
    {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Photo.class);
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.groupProperty("owner"));
            projectionList.add(Projections.rowCount());
            criteria.setProjection(projectionList);
            List results = criteria.list();

            HashMap<String, Long> resultsMap = new HashMap<String, Long>();
            for (Iterator it = results.iterator(); it.hasNext();) {
                Object[] row = (Object[]) it.next();
                String owner = (String) row[0];
                resultsMap.put(owner, (Long) row[1]);
            }
            return resultsMap;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    @Transactional
    public HashMap<String, Long> mostActive()
    {
        try
        {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Photo.class);
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.groupProperty("owner"));
            projectionList.add(Projections.rowCount(), "count");
            criteria.setProjection(projectionList);
            criteria.addOrder(Order.desc("count"));
            criteria.setMaxResults(1);
            List results = criteria.list();

            HashMap<String, Long> resultsMap = new HashMap<String, Long>();
            for (Iterator it = results.iterator(); it.hasNext();) {
                Object[] row = (Object[]) it.next();
                String owner = (String) row[0];
                resultsMap.put(owner, (Long) row[1]);
            }
            return resultsMap;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}


