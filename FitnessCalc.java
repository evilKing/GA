package com.ga.tsp;

public class FitnessCalc {

	static byte[] solution = new byte[64];

	/* Public methods */
	// Set a candidate solution as a byte array
	public static void setSolution(byte[] newSolution) {
		solution = newSolution;
	}

	// ���ó�ʼ������ѻ�������
	static void setSolution(String newSolution) {
		solution = new byte[newSolution.length()];

		for (int i = 0; i < newSolution.length(); i++) {
			String character = newSolution.substring(i, i + 1);
			if (character.contains("0") || character.contains("1")) {
				solution[i] = Byte.parseByte(character);
			} else {
				solution[i] = 0;
			}
		}
	}

	static int getFitness(Individual individual) {
		int fitness = 0;
		// �˴���Ӧ�Ⱥ���������Ϊ ��ǰ����Ļ���������Ŀ��������еİ�λ���ƶ�
		for (int i = 0; i < individual.size() && i < solution.length; i++) {
			if (individual.getGene(i) == solution[i]) {
				fitness++;
			}
		}
		return fitness;
	}

	// ���ŵĻ���������Ӧ�Ⱥ���ֵ���˴�������Ӧ�Ⱥ�������
	static int getMaxFitness() {
		int maxFitness = solution.length;
		return maxFitness;
	}
}