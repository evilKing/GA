package com.ga.math;
public class GA {

	private int ChrNum = 10;	//Ⱦɫ������
	private String[] ipop = new String[ChrNum];	 	//һ����Ⱥ��Ⱦɫ������
	private int generation = 0; 	//Ⱦɫ�����
	public static final int GENE = 46; 		//������
	private double bestfitness = Double.MAX_VALUE;  //�������Ž�
	private int bestgenerations;   	//�����Ӵ��븸������õ�Ⱦɫ��
	private String beststr;   		//���Ž��Ⱦɫ��Ķ�������
	
	/**
	 * ��ʼ��һ��Ⱦɫ�壨�ö������ַ�����ʾ��
	 */
	private String initChr() {
		String res = "";
		for (int i = 0; i < GENE; i++) {
			if (Math.random() > 0.5) {
				res += "0";
			} else {
				res += "1";
			}
		}
		return res;
	}

	/**
	 * ��ʼ��һ����Ⱥ(10��Ⱦɫ��)
	 */
	private String[] initPop() {
		String[] ipop = new String[ChrNum];
		for (int i = 0; i < ChrNum; i++) {
			ipop[i] = initChr();
		}
		return ipop;
	}

	/**
	 * ��Ⱦɫ��ת����x,y������ֵ
	 */
	private double[] calculatefitnessvalue(String str) {

		//��������ǰ23λΪx�Ķ������ַ�������23λΪy�Ķ������ַ���
		int a = Integer.parseInt(str.substring(0, 23), 2);      
		int b = Integer.parseInt(str.substring(23, 46), 2);

		double x =  a * (6.0 - 0) / (Math.pow(2, 23) - 1);    //x�Ļ���
		double y =  b * (6.0 - 0) / (Math.pow(2, 23) - 1);    //y�Ļ���

		//���Ż��ĺ���
		double fitness = 3 - Math.sin(2 * x) * Math.sin(2 * x) 
				- Math.sin(2 * y) * Math.sin(2 * y);
		
		double[] returns = { x, y, fitness };
		return returns;

	}

	/**
	 * ����ѡ��
	 * ����Ⱥ����ÿ���������Ӧ��ֵ; 
	 * ���ɸ�����Ӧ��ֵ��������ĳ������ѡ�񽫽�����һ���ĸ���;
	 */
	private void select() {
		double evals[] = new double[ChrNum]; // ����Ⱦɫ����Ӧֵ
		double p[] = new double[ChrNum]; // ��Ⱦɫ��ѡ�����
		double q[] = new double[ChrNum]; // �ۼƸ���
		double F = 0; // �ۼ���Ӧֵ�ܺ�
		for (int i = 0; i < ChrNum; i++) {
			evals[i] = calculatefitnessvalue(ipop[i])[2];
			if (evals[i] < bestfitness){  // ��¼����Ⱥ�е���Сֵ�������Ž�
				bestfitness = evals[i];
				bestgenerations = generation;
				beststr = ipop[i];
			}

			F = F + evals[i]; // ����Ⱦɫ����Ӧֵ�ܺ�
		}

		for (int i = 0; i < ChrNum; i++) {
			p[i] = evals[i] / F;
			if (i == 0)
				q[i] = p[i];
			else {
				q[i] = q[i - 1] + p[i];
			}
		}
		/**
		 * ��ÿ��Ⱦɫ��ʹ�����̶�ѡ������������ for ѭ��
		 */
		for (int i = 0; i < ChrNum; i++) {
			double r = Math.random();
			if (r <= q[0]) {
				ipop[i] = ipop[0];
			} else {
				for (int j = 1; j < ChrNum; j++) {
					if (r < q[j]) {
						ipop[i] = ipop[j];
					}
				}
			}
		}
	}

	/**
	 * ������� ������Ϊ60%��ƽ��Ϊ60%��Ⱦɫ����н���
	 */
	private void cross() {
		String temp1, temp2;
		for (int i = 0; i < ChrNum; i++) {
			if (Math.random() < 0.60) {
				int pos = (int)(Math.random()*GENE)+1;     //posλ��ǰ������ƴ�����
				temp1 = ipop[i].substring(0, pos) + ipop[(i + 1) % ChrNum].substring(pos); 
				temp2 = ipop[(i + 1) % ChrNum].substring(0, pos) + ipop[i].substring(pos);
				ipop[i] = temp1;
				ipop[(i + 1) / ChrNum] = temp2;
			}
		}
	}

	/**
	 * ����ͻ����� 1%�������
	 */
	private void mutation() {
		for (int i = 0; i < 4; i++) {
			int num = (int) (Math.random() * GENE * ChrNum + 1);
			int chromosomeNum = (int) (num / GENE) + 1; // Ⱦɫ���

			int mutationNum = num - (chromosomeNum - 1) * GENE; // �����
			if (mutationNum == 0) 
				mutationNum = 1;
			chromosomeNum = chromosomeNum - 1;
			if (chromosomeNum >= ChrNum)
				chromosomeNum = 9;
			String temp;
			String a;   //��¼����λ������ı���
			if (ipop[chromosomeNum].charAt(mutationNum - 1) == '0') {    //������λ��Ϊ0ʱ
                a = "1";
			} else {   
				a = "0";
			}
			//������λ�����ס��жκ�βʱ��ͻ�����
			if (mutationNum == 1) {
				temp = a + ipop[chromosomeNum].substring(mutationNum);
			} else {
				if (mutationNum != GENE) {
					temp = ipop[chromosomeNum].substring(0, mutationNum -1) + a 
							+ ipop[chromosomeNum].substring(mutationNum);
				} else {
					temp = ipop[chromosomeNum].substring(0, mutationNum - 1) + a;
				}
			}
			//��¼�±�����Ⱦɫ��		
			ipop[chromosomeNum] = temp;
		}
	}

	public static void main(String args[]) {

		GA Tryer = new GA();
		Tryer.ipop = Tryer.initPop(); //������ʼ��Ⱥ
		String str = "";
		
		//��������
		for (int i = 0; i < 100000; i++) {
			Tryer.select();
			Tryer.cross();
			Tryer.mutation();
			Tryer.generation = i;
		}
		
		double[] x = Tryer.calculatefitnessvalue(Tryer.beststr);

		str = "��Сֵ" + Tryer.bestfitness + '\n' + "��" 
		        + Tryer.bestgenerations + "��Ⱦɫ��:<" + Tryer.beststr + ">" + '\n' 
				+ "x=" + x[0] + '\n' + "y=" + x[1];

		System.out.println(str);

	}
}
