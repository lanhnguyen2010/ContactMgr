package vn.kms.launch.contactmgr.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.domain.greeting.GreetingRepository;
import vn.kms.launch.contactmgr.domain.greeting.GreetingSearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DaoTestConfig.class)
public class GreetingRepositoryTest extends TestCase {
    @Autowired
    private GreetingRepository repo;

    @Test
    public void testSearchGreetings() throws Exception {
        SearchResult<Greeting> result;
        GreetingSearchCriteria criteria = new GreetingSearchCriteria();
        criteria.setPageIndex(1);
        criteria.setPageSize(2);

        // search all
        result = repo.searchByCriteria(criteria);
        assertThat(result.getItems().size(), is(equalTo(2)));
        assertThat(result.getTotalItems(), is(equalTo(4)));

        // search by code
        criteria.setCode("en");
        result = repo.searchByCriteria(criteria);
        assertThat(result.getItems().size(), is(equalTo(1)));
        assertThat(result.getTotalItems(), is(equalTo(1)));
        criteria.setCode(null);

        // search by message
        criteria.setMessage("he*");
        result = repo.searchByCriteria(criteria);
        assertThat(result.getItems().size(), is(equalTo(1)));
        assertThat(result.getTotalItems(), is(equalTo(1)));

        criteria.setMessage("*o*");
        result = repo.searchByCriteria(criteria);
        assertThat(result.getItems().size(), is(equalTo(2)));
        assertThat(result.getTotalItems(), is(equalTo(3)));
        criteria.setMessage(null);

        // search by creation data
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");

        criteria.setCreationFrom(dateFormat.parse("15-04-01"));
        result = repo.searchByCriteria(criteria);
        assertThat(result.getItems().size(), is(equalTo(2)));
        assertThat(result.getTotalItems(), is(equalTo(3)));

        criteria.setCreationTo(dateFormat.parse("15-04-02"));
        result = repo.searchByCriteria(criteria);
        assertThat(result.getItems().size(), is(equalTo(2)));
        assertThat(result.getTotalItems(), is(equalTo(2)));

        criteria.setCreationFrom(null);
        criteria.setCreationTo(null);
    }
}
