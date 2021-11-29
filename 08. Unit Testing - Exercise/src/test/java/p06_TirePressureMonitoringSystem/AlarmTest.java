package p06_TirePressureMonitoringSystem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AlarmTest {
    private Alarm alarm;
    private Sensor sensor;

    @Before
    public void setUp() {
        this.sensor = Mockito.mock(Sensor.class);
        this.alarm = new Alarm(sensor);
    }

    @Test
    public void testShouldProperlyInitializeAlarm() {
        Sensor sensor = new Sensor();
        Alarm alarm = new Alarm(sensor);
        assertNotNull(sensor);
        assertNotNull(alarm);
    }

    @Test
    public void testShouldCheckWhenPsiIsAboveAllowed() {
        Mockito.when(this.sensor.popNextPressurePsiValue()).thenReturn(22.0);
        this.alarm.check();
        assertTrue(this.alarm.getAlarmOn());
    }

    @Test
    public void testShouldCheckWhenPsiIsBelowAllowed() {
        Mockito.when(this.sensor.popNextPressurePsiValue()).thenReturn(16.0);
        this.alarm.check();
        assertTrue(this.alarm.getAlarmOn());
    }

    @Test
    public void testShouldReturnTheCorrectAlarmState() {
        assertFalse(this.alarm.getAlarmOn());
        Mockito.when(this.sensor.popNextPressurePsiValue()).thenReturn(2.0);
        this.alarm.check();
        assertTrue(this.alarm.getAlarmOn());
    }
}