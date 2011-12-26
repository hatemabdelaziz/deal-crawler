/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.dao;

import deals.entity.Deals;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;

/**
 *
 * @author SAMAR
 */
public class DealDao extends HibernateDaoSupport {

    @Transactional
    public Object get(Class klass, int id) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().get(klass, id);
    }

    @Transactional
    public Object get(Class klass, Integer id) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().get(klass, id);
    }

    @Transactional
    public Object get(Class klass, Long id) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().get(klass, id);
    }

    @Transactional
    public Object get(Class klass, String id) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().get(klass, id);
    }

    @Transactional
    public void save(Object obj) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(obj);
    }

    @Transactional
    public void update(Object obj) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().update(obj);
    }

    @Transactional
    public void delete(Object obj) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().delete(obj);
    }
    
        @Transactional
    public void deleteAllDealsByFromSite() {
            String query = "truncate deals";
        getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(query);
    }

    @Transactional
    public List findAll(Class clazz) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().find("from " + clazz.getName());
    }

    @Transactional
    public void saveOrUpdate(Object obj) {
        getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(obj);

    }

    /**
     * Find objects
     *
     * @param className
     * @param attr
     * @param keys
     * @param values
     * @param op
     * @return
     * @throws ClassNotFoundException
     */
    @Transactional
    public List find(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints) throws ClassNotFoundException {
        Criteria crit = buildQuery(className, attr, constraints);
        if (attr.get("start") != null) {
            crit.setFirstResult((Integer) attr.get("start"));
        }
        if (attr.get("results") != null) {
            crit.setMaxResults((Integer) attr.get("results"));
        }
        return crit.list();
    }

    /**
     * Find objects
     *
     * @param className
     * @param attr
     * @param keys
     * @param values
     * @param op
     * @return
     * @throws ClassNotFoundException
     */
    @Transactional
    public List find(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints, Hashtable<String, Object> sqlConstraints) throws ClassNotFoundException {
        Criteria crit = buildQuery(className, attr, constraints, sqlConstraints);
        if (attr.get("start") != null) {
            crit.setFirstResult((Integer) attr.get("start"));
        }
        if (attr.get("results") != null) {
            crit.setMaxResults((Integer) attr.get("results"));
        }
        return crit.list();
    }

    /**
     * Find objects
     *
     * @param className
     * @param attr
     * @param constraints
     * @param alises
     * @return
     * @throws ClassNotFoundException
     */
    @Transactional
    public List find(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints, Map<String, String> alises) throws ClassNotFoundException {
        Criteria crit = buildQuery(className, attr, constraints, alises);
        if (attr.get("start") != null) {
            crit.setFirstResult((Integer) attr.get("start"));
        }
        if (attr.get("results") != null) {
            crit.setMaxResults((Integer) attr.get("results"));
        }
        return crit.list();
    }

    /**
     * Count number of objects matched
     *
     * @param className
     * @param attr
     * @param keys
     * @param values
     * @param op
     * @return
     * @throws ClassNotFoundException
     */
    @Transactional
    public long count(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints) throws ClassNotFoundException {

        Criteria crit = buildQuery(className, attr, constraints);

        List objs = crit.list();

        return objs == null ? 0 : objs.size();
    }

    /**
     * Count number of objects matched
     *
     * @param className
     * @param attr
     * @param keys
     * @param values
     * @param op
     * @return
     * @throws ClassNotFoundException
     */
    @Transactional
    public long count(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints, Hashtable<String, Object> sqlConstraints) throws ClassNotFoundException {

        Criteria crit = buildQuery(className, attr, constraints, sqlConstraints);

        List objs = crit.list();

        return objs == null ? 0 : objs.size();
    }

    @Transactional
    public long count(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints, Map<String, String> alises) throws ClassNotFoundException {

        Criteria crit = buildQuery(className, attr, constraints, alises);

        List objs = crit.list();

        return objs == null ? 0 : objs.size();
    }

    /**
     * Build query
     *
     * @param className
     * @param attr
     * @param keys
     * @param values
     * @param op
     * @return
     * @throws ClassNotFoundException
     */
    private Criteria buildQuery(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints) throws ClassNotFoundException {

        Criteria crit = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(className);

        crit = addConstraints(crit, constraints);

        if (attr.get("dir") != null) {
            if (((String) attr.get("dir")).equalsIgnoreCase("asc")) {
                crit.addOrder(Order.asc((String) attr.get("sort")));
            } else {
                crit.addOrder(Order.desc((String) attr.get("sort")));
            }
        }
        if (attr.get("qtype") != null) {
            crit.add(Restrictions.like((String) attr.get("qtype"), "%" + attr.get("query") + "%"));
        }
        if (attr.get("subCriteria") != null) {
            Hashtable sub = (Hashtable) attr.get("subCriteria");
            Iterator Keys = sub.keySet().iterator();
            while (Keys.hasNext()) {
                String key = (String) Keys.next();
                String objectName = key.substring(0, key.indexOf("."));
                String probertyName = key.replace(objectName + ".", "");
                crit = addSubCriteria(crit, objectName, probertyName, sub.get(key) + "");
            }

        }

        return crit;
    }

    /**
     * Build query
     *
     * @param className
     * @param attr
     * @param keys
     * @param values
     * @param op
     * @return
     * @throws ClassNotFoundException
     */
    private Criteria buildQuery(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints, Hashtable<String, Object> sqlConstraints) throws ClassNotFoundException {

        Criteria crit = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(className);

        crit = addConstraints(crit, constraints);
        crit = addSqlConstraints(crit, sqlConstraints);

        if (attr.get("dir") != null) {
            if (((String) attr.get("dir")).equalsIgnoreCase("asc")) {
                crit.addOrder(Order.asc((String) attr.get("sort")));
            } else {
                crit.addOrder(Order.desc((String) attr.get("sort")));
            }
        }
        if (attr.get("qtype") != null) {
            crit.add(Restrictions.like((String) attr.get("qtype"), "%" + attr.get("query") + "%"));
        }
        if (attr.get("subCriteria") != null) {
            Hashtable sub = (Hashtable) attr.get("subCriteria");
            Iterator Keys = sub.keySet().iterator();
            while (Keys.hasNext()) {
                String key = (String) Keys.next();
                String objectName = key.substring(0, key.indexOf("."));
                String probertyName = key.replace(objectName + ".", "");
                crit = addSubCriteria(crit, objectName, probertyName, sub.get(key) + "");
            }

        }

        return crit;
    }

    /**
     * Build query
     *
     * @param className
     * @param attr
     * @param constraints
     * @param alises
     * @return
     * @throws ClassNotFoundException
     */
    private Criteria buildQuery(Class className, Hashtable<String, Object> attr, Hashtable<String, Object> constraints, Map<String, String> alises) throws ClassNotFoundException {

        Criteria crit = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(className);

        crit = addConstraints(crit, constraints);
        crit = addAlises(crit, alises);

        if (attr.get("dir") != null) {
            if (((String) attr.get("dir")).equalsIgnoreCase("asc")) {
                crit.addOrder(Order.asc((String) attr.get("sort")));
            } else {
                crit.addOrder(Order.desc((String) attr.get("sort")));
            }
        }
        if (attr.get("qtype") != null) {
            crit.add(Restrictions.like((String) attr.get("qtype"), "%" + attr.get("query") + "%"));
        }
        if (attr.get("subCriteria") != null) {
            Hashtable sub = (Hashtable) attr.get("subCriteria");
            Iterator Keys = sub.keySet().iterator();
            while (Keys.hasNext()) {
                String key = (String) Keys.next();
                String objectName = key.substring(0, key.indexOf("."));
                String probertyName = key.replace(objectName + ".", "");
                crit = addSubCriteria(crit, objectName, probertyName, sub.get(key) + "");
            }

        }

        return crit;
    }

    /**
     * Add external constraints
     *
     * @param crit
     * @param keys
     * @param values
     * @return
     */
    public Criteria addConstraints(Criteria crit, Hashtable<String, Object> constraints) {
        if (constraints != null) {
            for (String key : constraints.keySet()) {
                crit.add(Restrictions.like(key, constraints.get(key)));
            }
        }

        return crit;
    }

    /**
     * Add external constraints
     *
     * @param crit
     * @param keys
     * @param values
     * @return
     */
    public Criteria addSqlConstraints(Criteria crit, Hashtable<String, Object> sqlConstraints) {
        if (sqlConstraints != null) {
            for (String key : sqlConstraints.keySet()) {
                crit.add(Restrictions.sqlRestriction((String) sqlConstraints.get(key)));
            }
        }

        return crit;
    }

    /**
     * Add external constraints
     *
     * @param crit
     * @param keys
     * @param values
     * @return
     */
    public Criteria addAlises(Criteria crit, Map<String, String> alises) {
        if (alises != null) {
            for (String key : alises.keySet()) {
                crit.createAlias(key, alises.get(key));
            }
        }

        return crit;
    }

    @Transactional
    public Boolean isNewObjectUnique(String objectName, String propertyName, String propertyValue) {
        String query = "from " + objectName + " where " + "" + propertyName + " = :poperty ";
        Query hql = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
        hql.setParameter("poperty", propertyValue);
        if (hql.list() == null || hql.list().size() == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public Boolean isEditObjectUnique(String objectName, String propertyName, String propertyValue, String identiferName, Integer IdentferValue) {
        String query = "from " + objectName + "  where " + "" + propertyName + " = :poperty and " + identiferName + "!= :id ";
        Query hql = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
        hql.setParameter("poperty", propertyValue);
        hql.setParameter("id", IdentferValue);
        if (hql.list() == null || hql.list().size() == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public Boolean isEditObjectUnique(String objectName, String propertyName, Integer propertyValue, String identiferName, Integer IdentferValue) {
        String query = "from " + objectName + "  where " + "" + propertyName + " = :poperty and " + identiferName + "!= :id ";
        Query hql = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
        hql.setParameter("poperty", propertyValue);
        hql.setParameter("id", IdentferValue);
        if (hql.list() == null || hql.list().size() == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public List searchObject(String objectName, String propertyName, String propertyValue) {
        String query = "from " + objectName + " where " + "" + propertyName + " = :poperty ";
        Query hql = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
        hql.setParameter("poperty", propertyValue);
        return hql.list();

    }

    @Transactional
    public List searchObject(String objectName, String propertyName, int propertyValue) {
        String query = "from " + objectName + " where " + "" + propertyName + " = :poperty ";
        Query hql = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
        hql.setInteger("poperty", propertyValue);
        return hql.list();

    }

    public Criteria addSubCriteria(Criteria criteria, String objectName, String propertyName, String propertyValue) {
        return criteria.createCriteria(objectName).add(Restrictions.eq(propertyName, isInt(propertyValue) ? Integer.parseInt(propertyValue) : propertyValue));

    }

    @Transactional
    public List findWithConditions(Class class1, String column, String value) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("from " + class1.getName() + " WHERE " + column + " = '" + value + "'").list();
    }

    public static boolean isInt(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }

    @Transactional
    public List<Object> getCityDeals(int langId, String cityName, String orderColumn, String orderWay , String fromSite) {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());
        //System.out.println("currentdate" + currentDate + "dateNow" + dateNow);
        String query = "from Deals deals where deals.city.name = :cityName and deals.fromSite = :fromSite and deals.language.id = :langId and deals.end > '" + dateNow + "' order by deals." + orderColumn + " " + orderWay;
        Query hql = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
        hql.setParameter("langId", langId);
        hql.setParameter("cityName", cityName);
         hql.setParameter("fromSite", fromSite);

        return hql.list();
    }
}
