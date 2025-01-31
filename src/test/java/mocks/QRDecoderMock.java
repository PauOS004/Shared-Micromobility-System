package mocks;

import data.VehicleID;
import exceptions.CorruptedImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

/**
 * Mock successful implementation of the {@link QRDecoder} interface.
 * Simulates the behavior of decoding a QR code to retrieve a VehicleID.
 */
public class QRDecoderMock implements QRDecoder {

    private boolean state = true;

    /**
     * Decodes a QR code image to retrieve a VehicleID.
     *
     * @param QRImg The QR code image.
     * @return A mock VehicleID if the state is valid.
     * @throws CorruptedImgException If the QR code is corrupted.
     */
    @Override
    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        if(state){
            return new VehicleID("VEH-12345");
        }
        throw new CorruptedImgException("QR code is corrupted");
    }

    /**
     * Sets the internal state of the decoder, determining whether the QR decoding succeeds or fails.
     *
     * @param state {@code true} for successful decoding, {@code false} to simulate a corrupted QR code.
     */
    public void setState(boolean state) {
        this.state = state;
    }
}
