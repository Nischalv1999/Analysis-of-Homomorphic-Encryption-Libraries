import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.List;

import java.lang.instrument.Instrumentation;

import security.DGK.DGKKeyPairGenerator;
import security.DGK.DGKOperations;
import security.DGK.DGKPrivateKey;
import security.DGK.DGKPublicKey;
import security.DGK.DGKSignature;
import security.elgamal.ElGamalCipher;
import security.elgamal.ElGamalPrivateKey;
import security.elgamal.ElGamalPublicKey;
import security.elgamal.ElGamalSignature;
import security.elgamal.ElGamal_Ciphertext;
import security.gm.GMCipher;
import security.gm.GMPrivateKey;
import security.gm.GMPublicKey;
import security.misc.CipherConstants;
import security.misc.HomomorphicException;
import security.misc.NTL;
import security.paillier.PaillierCipher;
import security.paillier.PaillierPrivateKey;
import security.paillier.PaillierPublicKey;
import security.paillier.PaillierSignature;
import security.socialistmillionaire.alice;
import security.socialistmillionaire.bob;

import java.security.SecureRandom;
import java.security.KeyPair;


public class Test {
    private static final int TEST = 100;
	private static final int SIZE = 100000; // Stress-Test
	private static final int KEY_SIZE = 1024;
	private static final double BILLION = BigInteger.TEN.pow(9).intValue();
	
    public static void DGK_Test(DGKPublicKey pubKey, DGKPrivateKey privKey) throws InvalidKeyException, SignatureException, HomomorphicException
	{
		System.out.println("-----------DGK TEST x" + SIZE + "--------------KEY: " + KEY_SIZE + "-----------");
		BigInteger base = DGKOperations.encrypt(NTL.generateXBitRandom(15), pubKey);

		BigInteger t = NTL.generateXBitRandom(15);
		System.out.println("Plain BigInteger size: "+ObjectSizeFetcher.getObjectSize(t));

		long start = 0;
		int temp=10;
		BigInteger tempe=DGKOperations.encrypt(temp,pubKey);
		System.out.println("Encrypted BigInteger size: "+ObjectSizeFetcher.getObjectSize(tempe));

		
		DGKSignature sig = new DGKSignature();
		sig.initSign(privKey);
		sig.update(new BigInteger("42").toByteArray());
		byte [] cert = sig.sign();

		start = System.nanoTime();
		for(int i = 0; i < SIZE;i++)
		{
			sig.initVerify(pubKey);
			sig.update(BigInteger.valueOf(i).toByteArray());
			if(sig.verify(cert))
			{
				System.out.println("DGK VALID AT: " + i);
			}
		}
		System.out.println("Time to complete signature: " + ((System.nanoTime() - start)/BILLION) + " seconds");
			
		start = System.nanoTime();
		for(int i = 0; i < SIZE; i++)
		{
			DGKOperations.encrypt(t, pubKey);
		}
		System.out.println("Time to complete encryption: " + ((System.nanoTime() - start)/BILLION) + "seconds");
	

		t = DGKOperations.encrypt(t, pubKey);

		// System.out.println("Encrypted BigInteger size: "+ObjectSizeFetcher.getObjectSize(t));

		start = System.nanoTime();
		for(int i = 0; i < SIZE; i++)
		{
			DGKOperations.decrypt(t, privKey);
		}
		System.out.println("Time to complete decryption: " + ((System.nanoTime() - start)/BILLION) + " seconds");
		
		start = System.nanoTime();
		for(int i = 0; i < SIZE; i++)
		{
			DGKOperations.add(base, t, pubKey);
		}
		System.out.println("Time to complete addition: " + ((System.nanoTime() - start)/BILLION) + " seconds");
	
		long exp =  NTL.generateXBitRandom(15).longValue();
		start = System.nanoTime();
		for(int i = 0; i < SIZE; i++)
		{
			DGKOperations.multiply(base, exp, pubKey);
		}
		System.out.println("Time to complete multiplication: " + ((System.nanoTime() - start)/BILLION) + " seconds");

		start = System.nanoTime();
		for(int i = 0; i < SIZE; i++)
		{
			DGKOperations.add_plaintext(base, exp, pubKey);
		}
		System.out.println("Time to complete addition (plaintext): " + ((System.nanoTime() - start)/BILLION) + " seconds");
	}
    public static void main(String[] args)
    {
// These Public Keys are made by Bob and automatically sent to Alice.
try{
	
// alice Niu = new alice(new Socket("localhost", 9254));

// DGKPublicKey pubKey = Niu.getDGKPublicKey();
// DGKPrivateKey privKey = Niu.getDGKPrivateKey();
int KEY_SIZE = 1024; //number of bits
final double BILLION = BigInteger.TEN.pow(9).intValue();
long start = 0;

// // Initialize instrumentation by calling premain method
// String agentArgs = "";
// Instrumentation instrumentation = ObjectSizeFetcher.getInstrumentation();
// ObjectSizeFetcher.premain(agentArgs, instrumentation);

// Object obj = new Object();
//     long size = ObjectSizeFetcher.getObjectSize(obj);

//     System.out.println("Size of object: " + size);

Runtime runtime = Runtime.getRuntime();
// Run the garbage collector
runtime.gc();
// Calculate the used memory


SecureRandom r = new SecureRandom();
DGKKeyPairGenerator p = new DGKKeyPairGenerator();
p.initialize(KEY_SIZE, r);
start = System.nanoTime();

KeyPair pe = p.generateKeyPair();
System.out.println("Time to generate keys: " + ((System.nanoTime() - start)/BILLION) + " seconds");
long memory1 = runtime.totalMemory() - runtime.freeMemory();
// System.out.println("Used memory1 is bytes: " + memory1);

DGKPublicKey pubKey = (DGKPublicKey) pe.getPublic();
DGKPrivateKey privKey = (DGKPrivateKey) pe.getPrivate();
System.out.println("Obj size: "+ObjectSizeFetcher.getObjectSize(pubKey));
System.out.println("Obj size: "+ObjectSizeFetcher.getObjectSize(privKey));




long memory2= runtime.totalMemory() - runtime.freeMemory();
// System.out.println("Used memory2 is bytes: " + memory2);

        Test.DGK_Test(pubKey,  privKey); 
    }

catch(InvalidKeyException inv){
inv.printStackTrace();

}
catch(SignatureException inv){
    inv.printStackTrace();
}
    // catch(HomomorphicException inv){
    //     inv.printStackTrace();
 catch (HomomorphicException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
        
    //     }
    //     catch(UnknownHostException inv){
    //         inv.printStackTrace();
            
    //         }
    //         catch(IOException inv){
    //             inv.printStackTrace();
                
    //             }
    //             catch(ClassNotFoundException inv){
    //                 inv.printStackTrace();
                    
    //                 }
        
    }	
}
