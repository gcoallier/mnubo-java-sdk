package integration;

import static java.lang.String.format;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import com.mnubo.java.sdk.client.models.DataSet;
import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.models.result.Result;
import com.mnubo.java.sdk.client.models.result.ResultSet;
import com.mnubo.java.sdk.client.services.MnuboSDKFactory;
import com.mnubo.java.sdk.client.spi.MnuboSDKClient;

/*
CHANGE_ME: YOU MUST REMOVE OR COMMENT OUT THE 'IGNORE' ANNOTATION TO RUN YOUR INTEGRATION TEST.
 */
@FixMethodOrder(NAME_ASCENDING)
public class SdkClientIntegrationTest {
    private final static Log log = LogFactory.getLog(SdkClientIntegrationTest.class);

    private final String HOSTNAME = "rest.sandbox.mnubo.com";

    /*
    CHANGE_ME: YOU MUST PUT HERE YOUR CONSUMER KEY, DON'T FORGET TO ASK MNUBO FOR THIS.
     */
    //private final String CONSUMER_KEY = "JYWYwGU0mZGoVSdPi06g39Gl0gDX4OSCcHhuHKXAOqvowDrMUj";
    private final String CONSUMER_KEY = "Dqq24kiHDAKTtwbqPnMafwq8WnJyfeYwa181yW8Vvc9abNCTVB";
    // private final String CONSUMER_KEY =
    // "PVmtWu6GSuQe6mCLSd0mEBCm6bQ1gBi8a8d3zr7FUtpkFHqZ74";
    
    /*
    CHANGE_ME: YOU MUST PUT HERE YOUR CONSUMER SECRET, DON'T FORGET TO ASK MNUBO FOR THIS.
     */
    //private final String CONSUMER_SECRET = "cO0Kl8F6DfzNVLlAaz2UXkQx2wpG8I6TftXwGnRpS0jwd37IyS";
    private final String CONSUMER_SECRET = "Gs25k00khkxy85SJH1ZShsQzmqo4EjsIck0b2FLrjDV9sKLhiA";
    // private final String CONSUMER_SECRET =
    // "LYB6lp5u5q3vNbB9Yr4mg4iIq4E7BDlXNp2miAN9bJcoTP2a0u";

    private final String DEVICE_ID = "MyDeviceTest01";
    
    private final List<String> BATCH_DEVICE_ID = new ArrayList<String>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OBJECT DEVICE ID(s) DESIRED IN OBJECT PUT REQUESTS (BATCH).
        YOU CAN ADD ONLY ONE OR MORE THAN ONE DEVICE ID AND IT HAS TO BE ADDED AS THE BELOW 
        EXAMPLE WITH A VALUE:
        add("MyDeviceTest01");
        add("MyDeviceTest02");
        add("MyDeviceTest03");
        add("MyDeviceTest04");
        etc
         */
        add("MyDeviceTestBatch01");
        add("MyDeviceTestBatch02");
    }};

    private final String OWNER_USERNAME = "MyOwnerTest01";
    
    private final List<String> BATCH_OWNER_USERNAME = new ArrayList<String>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER USERNAME DESIRED IN OWNER PUT REQUESTS (BATCH).
        YOU CAN ADD ONLY ONE OR MORE THAN ONE OWNER USERNAME AND IT HAS TO BE ADDED AS THE BELOW 
        EXAMPLE WITH A VALUE:
        add("MyOwnerTest01");
        add("MyOwnerTest02");
        add("MyOwnerTest03");
        add("MyOwnerTest04");
        etc
         */
        add("MyOwnerTestBatch01");
        add("MyOwnerTestBatch02");
    }};

    private final String DEVICE_ID_WITH_OWNER_LINK = "MyDeviceTest02";

    private final String OWNER_USERNAME_WITH_OWNER_LINK = "MyOwnerTest02";

    private final String EVENT_TYPE = "wind_direction";

    private final String OBJECT_TYPE = "ObjectType";
    
    private final List<String> BATCH_OBJECT_TYPE = new ArrayList<String>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE LIST OF OBJECT TYPE DESIRED IN OBJECT PUT REQUESTS (BATCH).
        YOU CAN ADD ONLY ONE OR MORE THAN ONE OBJECT TYPE AND IT HAS TO BE ADDED AS THE BELOW 
        EXAMPLE WITH A VALUE:
        add("ObjectType1");
        add("ObjectType2");
        add("ObjectType3");
        add("ObjectType4");
        etc
         */
        add("ObjectType1");
        add("ObjectType2");
    }};

    private final String OWNER_PASSWORD = "myPassword";
    
    private final List<String> BATCH_OWNER_PASSWORD = new ArrayList<String>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER PASSWORD DESIRED IN OWNER PUT REQUESTS (BATCH).
        YOU CAN ADD ONLY ONE OR MORE THAN ONE OWNER PASSWORD AND IT HAS TO BE ADDED AS THE BELOW 
        EXAMPLE WITH A VALUE:
        add("myPassword1");
        add("myPassword2");
        add("myPassword3");
        add("myPassword4");
        etc
         */
        add("myPasswordbatch1");
        add("myPasswordbatch2");
    }};

    private final Map<String, Object> OWNER_ATTRIBUTES = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OWNER POST REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
            // put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT
            // HERE ITS VALUE > ");
        put("x_registration_subdivision1_iso_code", "test123");
        put("x_registration_postal_code", "test123");
    }};
    
    private final Map<String, Object> OWNER_ATTRIBUTES2 = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OWNER POST REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
            // put(" < CHANGE_ME: PUT HERE THE ATTRIBUTE'S NAME > ", " < CHANGE_ME: PUT
            // HERE ITS VALUE > ");
        put("x_registration_subdivision1_iso_code", "test123");
        put("x_registration_postal_code", "test123");
    }};
    
    private final List<Map<String, Object>> BATCH_OWNER_ATTRIBUTES = new ArrayList<Map<String, Object>>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE MAP OF OWNER ATTRIBUTES DESIRED IN OWNER PUT REQUESTS FOR BATCH.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE MAP OF OWNER ATTRIBUTES AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        add(OWNER_ATTRIBUTES_BATCH1);
        add(OWNER_ATTRIBUTES_BATCH2);
        add(OWNER_ATTRIBUTES_BATCH3);
        add(OWNER_ATTRIBUTES_BATCH4);
        etc
        NOTE: THIS EXAMPLE SHOW A LIST(BATCH) OF ATTRIBUTES WITH ONLY ONE MAP OF OWNER_ATTRIBUTES, 
        BUT MULTIPLE MAP OF OWNER_ATTRIBUTES CAN BE ADDED
         */
        add(OWNER_ATTRIBUTES); // < CHANGE_ME: PUT HERE THE MAP OF OWNER ATTRIBUTES >
        add(OWNER_ATTRIBUTES2); // < CHANGE_ME: PUT HERE THE MAP OF OWNER ATTRIBUTES >
    }};

    private final Map<String, Object> OBJECT_ATTRIBUTES = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OBJECT POST REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put("color", "blue");
        put("vin", "123");
    }};
    
    private final Map<String, Object> OBJECT_ATTRIBUTES2 = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OBJECT POST REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put("color", "blue");
        put("vin", "123");
    }};
    
    private final List<Map<String, Object>> BATCH_OBJECT_ATTRIBUTES = new ArrayList<Map<String, Object>>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE MAP OF OBJECT ATTRIBUTES DESIRED IN OBJECT PUT REQUESTS FOR BATCH.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE MAP OF OBJECT ATTRIBUTES AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        add(OBJECT_ATTRIBUTES_BATCH1);
        add(OBJECT_ATTRIBUTES_BATCH2);
        add(OBJECT_ATTRIBUTES_BATCH3);
        add(OBJECT_ATTRIBUTES_BATCH4);
        etc
        NOTE: THIS EXAMPLE SHOW A LIST(BATCH) OF ATTRIBUTES WITH ONLY ONE MAP OF OWNER_ATTRIBUTES, 
        BUT MULTIPLE MAP OF OWNER_ATTRIBUTES CAN BE ADDED
         */
        add(OBJECT_ATTRIBUTES); // < CHANGE_ME: PUT HERE THE MAP OF OBJECT ATTRIBUTES >
        add(OBJECT_ATTRIBUTES2); // < CHANGE_ME: PUT HERE THE MAP OF OBJECT ATTRIBUTES >
    }};

    private final Map<String, Object> OWNER_ATTRIBUTES_2_UPDATE = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OWNER PUT REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put("x_registration_subdivision1_iso_code", "4321");
        put("x_registration_postal_code", "7654321`");
    }};

    private final Map<String, Object> OBJECT_ATTRIBUTES_2_UPDATE = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE OWNER ATTRIBUTES DESIRED IN OBJECT PUT REQUESTS. REMEMBER, EACH ATTRIBUTE NAME
        HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put("color", "yellow");
            put("vin", "999");
    }};

    private final Map<String, Object> TIMESERIES_2_POST = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE TIMESERIES DESIRED IN POST EVENTS. REMEMBER, EACH TIMESERIE NAME HAS TO EXIST
        IN YOUR OBJECT MODEL DEFINITION AND ITS VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
        put("MyBooleanAttribute", true);
        put("MyStringAttribute", "String");
        put("MyIntAttribute", 33);
        put("MyDoubleAttribute", 10.55);
        put("MyListAttribute", ["item1", "item2"]);
        put("MyDatetimeAttribute", "2015-08-28T15:08:37.577Z");
        etc
         */
        put("speed", 24.666);
    }};

    private final Map<String, Object> EXTRA_TIMESERIES_2_POST = new HashMap<String, Object>() {{
        /*
        CHANGE_ME: YOU MUST ADD HERE THE EXTRAS TIMESERIES DESIRED IN A SECOND POST EVENTS. THIS CAN BE THE SAME TO
        'TIMESERIES_2_POST' OR NOT. REMEMBER, EACH TIMESERIE NAME HAS TO EXIST IN YOUR OBJECT MODEL DEFINITION AND ITS
        VALUE HAS TO MATCH ALSO WITH YOUR OBJECT MODEL DEFINITION.
        YOU CAN ADD ONLY ONE OR MORE THAN ONE ATTRIBUTE AND IT HAS TO BE ADDED AS THE BELOW EXAMPLE WITH A NAME AND A
        VALUE.
         */
        put("fuel", 12.1);
    }};
    
    private final String QUERY = 
        /*
        CHANGE_ME: YOU MUST MODIFY HERE THE QUERY SENT TO THE SEARCH API(S)
        Example: "{ \"from\": \"event\", \"select\": [ {\"value\": \"speed\"} ] }"
        */
        "{ CHANGE_ME }";

    MnuboSDKClient client = MnuboSDKFactory.getClient(HOSTNAME, CONSUMER_KEY, CONSUMER_SECRET);

    @Test
    public void T001_CreateOwner() {
        try {

            //Owner to be created
            Owner owner = Owner
                    .builder()
                    .withUsername(OWNER_USERNAME)
                    .withPassword(OWNER_PASSWORD)
                    .withAttributes(OWNER_ATTRIBUTES)
                    .build();

            //Posting Owner
            client.getOwnerClient().create(owner);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T005_UpdateOwner() {
        try {

            //Owner to be updated
            Owner owner = Owner
                    .builder()
                    .withAttributes(OWNER_ATTRIBUTES_2_UPDATE)
                    .build();

            //Putting Owner
            client.getOwnerClient().update(owner, OWNER_USERNAME);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }
    
    @Test
    public void T007_CreateUpdateOwnersBatch() {
        try {
            // Make sure the number of username / password / attributes are the same
            assertThat(BATCH_OWNER_USERNAME.size(), equalTo(BATCH_OWNER_PASSWORD.size()));
            assertThat(BATCH_OWNER_USERNAME.size(), equalTo(BATCH_OWNER_ATTRIBUTES.size()));

            List<Owner> owners = new ArrayList<>();
            
            // Owners to be created
            for (int i = 0; i < BATCH_OWNER_USERNAME.size(); i++) {
                owners.add(Owner.builder()
                                .withUsername(BATCH_OWNER_USERNAME.get(i))
                                .withPassword(BATCH_OWNER_PASSWORD.get(i))
                                .withAttributes(BATCH_OWNER_ATTRIBUTES.get(i))
                                .build());
            }

            // PUT Batch of Owners
            List<Result> results = client.getOwnerClient().createUpdate(owners);

            assertNotNull("The list of results for owners is NULL", results);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T010_DeleteOwner() {
        try {

            //Owner to be created & deleted
            String userName = "createMeThenDeleteMe";

            Owner owner = Owner
                    .builder()
                    .withUsername(userName)
                    .withPassword(OWNER_PASSWORD)
                    .build();

            //Posting Owner
            client.getOwnerClient().create(owner);

            //deleting Owner
            client.getOwnerClient().delete(userName);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T015_CreateObject() {
        try {

            //Object to be created
            SmartObject object = SmartObject
                    .builder()
                    .withDeviceId(DEVICE_ID)
                    .withObjectType(OBJECT_TYPE)
                    .withAttributes(OBJECT_ATTRIBUTES)
                    .build();

            //Posting Object
            client.getObjectClient().create(object);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T020_CreateObjectWithOwner() {
        try {

            //Owner to be linked
            Owner owner = Owner
                    .builder()
                    .withUsername(OWNER_USERNAME_WITH_OWNER_LINK)
                    .withPassword(OWNER_PASSWORD)
                    .build();

            //Posting Owner to be linked, this must be posted before to link it to an object.
            client.getOwnerClient().create(owner);

            //Object to be created,
            SmartObject object = SmartObject
                    .builder()
                    .withDeviceId(DEVICE_ID_WITH_OWNER_LINK)
                    .withObjectType(OBJECT_TYPE)
                    .withAttributes(OBJECT_ATTRIBUTES)
                    .withOwner(OWNER_USERNAME_WITH_OWNER_LINK)
                    .build();

            //Posting Object
            client.getObjectClient().create(object);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T025_UpdateObject() {
        try {

            //Object to be updated
            SmartObject object = SmartObject
                    .builder()
                    .withAttributes(OBJECT_ATTRIBUTES_2_UPDATE)
                    .build();

            //Putting Object
            client.getObjectClient().update(object, DEVICE_ID);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }
    
    @Test
    public void T027_CreateUpdateObjectsBatch() {
        try {
            // Make sure the number of device id / object type / attributes are the same
            assertThat(BATCH_DEVICE_ID.size(), equalTo(BATCH_OBJECT_TYPE.size()));
            assertThat(BATCH_DEVICE_ID.size(), equalTo(BATCH_OBJECT_ATTRIBUTES.size()));

            List<SmartObject> objects = new ArrayList<>();
            
            // Objects to be created
            for (int i = 0; i < BATCH_DEVICE_ID.size(); i++) {
                objects.add(SmartObject.builder()
                                       .withDeviceId(BATCH_DEVICE_ID.get(i))
                                       .withObjectType(BATCH_OBJECT_TYPE.get(i))
                                       .withAttributes(BATCH_OBJECT_ATTRIBUTES.get(i))
                                       .build());
            }

            // PUT Batch of Objects
            List<Result> results = client.getObjectClient().createUpdate(objects);

            assertNotNull("The list of results for objects is NULL", results);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T030_DeleteObject() {
        try {

            //Object to be created & deleted
            String deviceId = "createMeThenDeleteMe";

            SmartObject object = SmartObject
                    .builder()
                    .withDeviceId(deviceId)
                    .withObjectType(OBJECT_TYPE)
                    .build();

            //Posting Owner
            client.getObjectClient().create(object);

            //deleting Owner
            client.getObjectClient().delete(deviceId);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T035_OwnerClaimingObject() {
        try {


            //claiming Owner. Both Object and Owner must have been created before.
            client.getOwnerClient().claim(OWNER_USERNAME, DEVICE_ID);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T040_PostingOneEvent() {
        try {

            //event to be posted
            Event event = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withSmartObject(DEVICE_ID)
                    .withTimeseries(TIMESERIES_2_POST)
                    .build();

            //Posting event
            List<Result> results = client.getEventClient().send(Arrays.asList(event));

            assertNotNull("The list of results for events is NULL", results);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T045_PostingEvents() {
        try {

            //events to be posted
            Event event1 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withSmartObject(DEVICE_ID)
                    .withTimeseries(TIMESERIES_2_POST)
                    .build();

            Event event2 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withSmartObject(DEVICE_ID)
                    .withTimeseries(EXTRA_TIMESERIES_2_POST)
                    .build();

            List<Event> events = new ArrayList<Event>();
            events.add(event1);
            events.add(event2);

            //Posting events
            List<Result> results = client.getEventClient().send(events);

            assertNotNull("The list of results for events is NULL", results);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T050_PostingEventsWithDeviceId() {
        try {

            //events to be posted
            Event event1 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withTimeseries(TIMESERIES_2_POST)
                    .build();

            Event event2 = Event
                    .builder()
                    .withEventType(EVENT_TYPE)
                    .withTimeseries(EXTRA_TIMESERIES_2_POST)
                    .build();

            List<Event> events = new ArrayList<Event>();
            events.add(event1);
            events.add(event2);

            //Posting events
            List<Result> results = client.getEventClient().send(DEVICE_ID, events);

            assertNotNull("The list of results for events is NULL", results);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T055_CleanUp() {
        client.getOwnerClient().delete(OWNER_USERNAME);
        client.getOwnerClient().delete(OWNER_USERNAME_WITH_OWNER_LINK);
        for (String batchUsername : BATCH_OWNER_USERNAME) {
            client.getOwnerClient().delete(batchUsername);
        }
        client.getObjectClient().delete(DEVICE_ID);
        client.getObjectClient().delete(DEVICE_ID_WITH_OWNER_LINK);
        for (String batchDeviceId : BATCH_DEVICE_ID) {
            client.getObjectClient().delete(batchDeviceId);
        }
    }
    
    @Ignore
    @Test
    public void T060_SearchBasic() {
        try {
            // Perform a Basic Search
            ResultSet searchResultSet = client.getSearchClient().search(QUERY);
            
            assertNotNull("Search Result is NULL", searchResultSet);

        }
        catch (HttpClientErrorException ex) {

            log.error(format("Error code: %s, Error Message: %s", ex.getStatusCode(), ex.getResponseBodyAsString()),
                    ex);
            fail();
        }
        catch (Exception ex) {

            log.error(format("Error Message: %s", ex.getMessage()), ex);
            fail();
        }
    }

    @Test
    public void T065_SearchDataset() {
        try {
            // Perform a Basic Search
            List<DataSet> datasets = client.getSearchClient().getDatasets();
            
            assertNotNull(datasets);
            assertTrue("Dataset size = 0", datasets.size() > 0);

        } catch (HttpClientErrorException ex) {

            log.error(
                    format(
                            "Error code: %s, Error Message: %s",
                            ex.getStatusCode(),
                            ex.getResponseBodyAsString()
                    ),
                    ex
            );
            fail();
        } catch (Exception ex) {

            log.error(format("Error Message: %s",ex.getMessage()), ex);
            fail();
        }
    }
}
