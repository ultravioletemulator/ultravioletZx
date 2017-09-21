import org.testng.Assert;
import org.testng.annotations.Test;
import org.ultraviolet.spectrum.machines.Spectrum48k;

/**
 * Created by developer on 21/09/2017.
 */
public class TestSpectrum {

	@Test
	public void testSpectrum48k() {
		Spectrum48k spectrum48k = new Spectrum48k();
		spectrum48k.init();
		try {
			spectrum48k.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
