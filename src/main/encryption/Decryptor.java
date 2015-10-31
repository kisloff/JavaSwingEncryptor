package main.encryption;

import main.utils.MathUtils;
import main.utils.StringUtils;

import java.util.Arrays;

public class Decryptor {

    public static String decrypt(String string){
        int[] encyptedArray = StringUtils.convertStringToArray(string);
        int[] unshiftedInput = MathUtils.shift(encyptedArray, EncryptionProtocol.REST);
        int[] gamma = EncryptionProtocol.gamma;
        int[] decrypted = MathUtils.sumArrays(unshiftedInput, gamma);
        return Arrays.toString(decrypted);
    }
}
