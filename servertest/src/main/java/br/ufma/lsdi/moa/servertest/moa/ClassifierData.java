package br.ufma.lsdi.moa.servertest.moa;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import com.yahoo.labs.samoa.instances.Prediction;

import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.TimingUtils;
import moa.core.Utils;
import moa.streams.generators.RandomRBFGenerator;

public class ClassifierData {
	private Classifier learner;
	private int numberSamplesCorrect = 0;
	private int totalSamples = 0;

	public int classifier(Instance inst) {

		int class_id = Utils.maxIndex(learner.getVotesForInstance(inst));
		if (learner.correctlyClassifies(inst)) {
			numberSamplesCorrect++;
		}
		totalSamples++;
		learner.trainOnInstance(inst);
		return class_id;
	}

	public void start() {
		learner = new HoeffdingTree();

		learner.setModelContext(getHeader());
		learner.prepareForUse();

	}

	public double acurracy() {
		return 100.0 * (double) numberSamplesCorrect / (double) totalSamples;
	}

	public InstancesHeader getHeader() {
		
		return GeneratedData.getHeader();
	}
}
