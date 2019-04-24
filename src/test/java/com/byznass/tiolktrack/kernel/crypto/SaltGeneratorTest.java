package com.byznass.tiolktrack.kernel.crypto;

import com.byznass.tiolktrack.kernel.TiolkTrackException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SaltGenerator.class)
public class SaltGeneratorTest {

	private SaltGenerator saltGenerator;

	@Before
	public void setUp() {

		PowerMockito.mockStatic(SecureRandom.class);

		saltGenerator = new SaltGenerator();
	}

	@Test(expected = TiolkTrackException.class)
	public void givenNoSuchAlgorithmExceptionThenThrowTiolkTrackException() throws Exception {

		PowerMockito.when(SecureRandom.getInstance("SHA1PRNG")).thenThrow(NoSuchAlgorithmException.class);

		saltGenerator.generateSalt();
	}

	@Test
	public void givenRequestForGeneratingSaltThenSucceed() throws Exception {

		SecureRandom secureRandom = mock(SecureRandom.class);
		PowerMockito.when(SecureRandom.getInstance("SHA1PRNG")).thenReturn(secureRandom);

		doAnswer(invocation -> {

			byte[] salt = invocation.getArgument(0);
			Arrays.fill(salt, (byte) 1);

			return null;
		}).when(secureRandom).nextBytes(any());


		byte[] actualSalt = saltGenerator.generateSalt();

		byte[] expectedSalt = new byte[32];
		Arrays.fill(expectedSalt, (byte) 1);

		assertArrayEquals(expectedSalt, actualSalt);
	}
}