package org.homelinux.net.code.multiutils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipValidatorTest {

    @Test
    public void regularFileShouldNotBeValidZip() throws Exception {
        Path file = Paths.get(getClass().getResource("/regularFile.txt").getFile());
        Assert.assertFalse(ZipValidator.isValidZip(file));
    }

    @Test
    public void zipFileShouldBeValidZip() throws Exception {
        Path file = Paths.get(getClass().getResource("/proper.zip").getFile());
        Assert.assertTrue(ZipValidator.isValidZip(file));
    }

    @Test
    public void brokenZipFileShouldBeInValidZip() throws Exception {
        Path file = Paths.get(getClass().getResource("/broken.zip").getFile());
        Assert.assertFalse(ZipValidator.isValidZip(file));
    }
}
