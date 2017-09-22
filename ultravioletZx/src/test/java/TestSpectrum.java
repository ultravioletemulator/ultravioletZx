import org.testng.Assert;
import org.testng.annotations.Test;
import org.ultraviolet.spectrum.exceptions.ZxException;
import org.ultraviolet.spectrum.machines.Spectrum48k;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by developer on 21/09/2017.
 */
public class TestSpectrum {

	@Test
	public void testSpectrum48k() {
		Spectrum48k spectrum48k = new Spectrum48k();
		try {
			spectrum48k.init();
			spectrum48k.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (ZxException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
