package com.pagination;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.pagination.entities.Photo;
import com.pagination.models.PhotoModel;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import gherkin.formatter.model.DataTableRow;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.spring.SpringTransactionHooks;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class StepDefsIntegrationTest extends SpringIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws SQLException, DatabaseUnitException {

        IDataSet dataSet = loadDataSet();
        IDatabaseConnection databaseConnection = new DatabaseDataSourceConnection(dataSource);
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);

    }

    protected IDataSet loadDataSet() throws DataSetException {
        String name = "dbunit-dataset.xml";
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        if (stream == null) {
            throw new IllegalStateException("Dbunit file '" + name + "' does not exist");
        }
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(stream);
        ReplacementDataSet rDataSet = new ReplacementDataSet(dataSet);
        Date date = new Date();
        Object param = new java.sql.Timestamp(date.getTime());
        Timestamp timestamp = new Timestamp(date.getTime());
        rDataSet.addReplacementObject("createdate", timestamp);
        rDataSet.addReplacementObject("updatedate", timestamp);
        return dataSet;
    }

    //photos test
    @When("^the client calls /photoByOwner$")
    public void the_client_calls_photos_by_owner() throws Throwable {
        executeGet("http://localhost:8082/photoByOwner");
    }

    @Then("^the client receives status code of (\\d+) with$")
    public void the_client_receives_status_code_of_with_results(int statusCode, DataTable userCounts) throws Throwable {

        HashMap<String, Long> expectedResultsMap = new HashMap<String, Long>();
        for(DataTableRow row:userCounts.getGherkinRows()) {
            String owner = row.getCells().get(0);
            Long count = new Long(row.getCells().get(1));
            expectedResultsMap.put(owner, count);
        }

        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));

        JSONObject obj = new JSONObject(latestResponse.getBody());
        boolean success = true;
        for (Iterator it = obj.keys(); it.hasNext();) {
            String owner = (String) it.next();
            long observedCount = ((Integer) obj.get(owner)).longValue();
            long expectedCount = expectedResultsMap.get(owner).longValue();
            if(expectedCount != observedCount) {
                success = false;
                break;
            }
        }
        assert(success);
    }


    @When("^the client calls /mostActive$")
    public void the_client_calls_mostActive() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        executeGet("http://localhost:8082/mostActive");
    }

    @Then("^the client receives status code of (\\d+) with owner (.+) and count (\\d+)$")
    public void the_client_receives_status_code_of_with_owner_Olivia_Jayden_and_count(int statusCode, String owner, int expectedCount) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
        JSONObject obj = new JSONObject(latestResponse.getBody());
        boolean success = true;
        if(obj.has(owner)) {
            long observedCount = ((Integer) obj.get(owner)).longValue();
            if(expectedCount != observedCount) {
                success = false;
            }
        }
        assert(success);
    }



    @After(value = {"@txn"}, order = 100)
    public void rollBackTransaction() {
    }
}