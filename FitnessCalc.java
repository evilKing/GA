package com.ga.tsp;

public class FitnessCalc {

	static byte[] solution = new byte[64];

	/* Public methods */
	// Set a candidate solution as a byte array
	public static void setSolution(byte[] newSolution) {
		solution = newSolution;
	}

	// 设置初始化的最佳基因序列
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
		// 此处适应度函数被设置为 当前个体的基因序列与目标基因序列的按位相似度
		for (int i = 0; i < individual.size() && i < solution.length; i++) {
			if (individual.getGene(i) == solution[i]) {
				fitness++;
			}
		}
		return fitness;
	}

	// 最优的基因序列适应度函数值，此处是有适应度函数决定
	static int getMaxFitness() {
		int maxFitness = solution.length;
		return maxFitness;
	}
}