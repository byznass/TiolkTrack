package com.byznass.tiolktrack.resource;

import com.byznass.tiolktrack.resource.dto.Location;
import com.byznass.tiolktrack.resource.handler.GetGpsById;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GpsTest {

    @Mock
    private GetGpsById getGpsById;

    private Gps gps;

    @Before
    public void setUp() {

        initMocks(this);

        gps = new Gps(getGpsById);
    }

    @Test
    public void whenGettingCurrentLocationOfGpdThenReturnCorrectResponse() {

        String gpsId = "xyz";
        Location expectedLocation = new Location("123", "456");
        when(getGpsById.execute(gpsId)).thenReturn(expectedLocation);

        Location actualLocation = gps.getLocationById(gpsId);

        verify(getGpsById).execute(gpsId);
        assertEquals(expectedLocation, actualLocation);
    }

}