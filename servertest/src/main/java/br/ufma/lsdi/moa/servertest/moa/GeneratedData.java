package br.ufma.lsdi.moa.servertest.moa;

import java.util.Arrays;

import com.yahoo.labs.samoa.instances.DenseInstance;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import moa.core.InstanceExample;
import moa.streams.generators.RandomRBFGenerator;

public class GeneratedData {
	private RandomRBFGenerator stream;
	 
	public void start(){
		stream = new RandomRBFGenerator();
        stream.prepareForUse();
        
	}
	
	public Instance nextInstance(){
		Instance trainInst = stream.nextInstance().getData();
		return trainInst;
	}
	
	public static double [] getData(Instance trainInst,int attrsNum) {
		double [] dados=new double[attrsNum];
		for (int i = 0; i < attrsNum; i++) {
			dados[i]=trainInst.value(i);
		}
	
		return dados;
	}
	
	public static  Instance getInstance(double dados[],int attrsNum) {
		double new_array_attr[]=Arrays.copyOf(dados, attrsNum+1);
	
		
		Instance inst = new DenseInstance(1.0, new_array_attr);
        inst.setDataset(getHeader());
        inst.setClassValue(dados[attrsNum]);
        return inst;
        
	}
	
	public static  Instance getInstance(String dadosStr[],int attrsNum) {
		double dados[]=new double[dadosStr.length];
		for (int i = 0; i < dadosStr.length; i++) {
			dados[i]=Double.parseDouble(dadosStr[i]);
		}
		System.out.println(Arrays.toString(dados));
		return getInstance(dados, attrsNum);
	}
	
	public static InstancesHeader getHeader() {
		RandomRBFGenerator stream = new RandomRBFGenerator();
		stream.prepareForUse();

		return stream.getHeader();
	}

}
