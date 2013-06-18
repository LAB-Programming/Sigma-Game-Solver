package net.clonecomputers.lab.sigmagame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jscience.mathematics.number.ModuloInteger;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

public class Board {
	
	private final DenseMatrix<ModuloInteger> adjMatrix;
	private final Set<DenseVector<ModuloInteger>> kernals;
	private final Set<DenseVector<ModuloInteger>> moveCombs;
	public final DenseVector<ModuloInteger> ZERO_CONFIG;
	public final DenseVector<ModuloInteger> ONE_CONFIG;

	public Board(ModuloInteger[][] adjacencyMatrix) {
		if(adjacencyMatrix.length <= 0 || adjacencyMatrix[0].length <= 0) {
			throw new IllegalArgumentException("the adjacency matrix cannot have 0 as one of its dimensions");
		}
		if(adjacencyMatrix.length != adjacencyMatrix[0].length) {
			throw new IllegalArgumentException("the adjacency matrix must be square");
		}
		List<DenseVector<ModuloInteger>> list = new ArrayList<DenseVector<ModuloInteger>>();
		for(ModuloInteger[] a : adjacencyMatrix) {
			list.add(DenseVector.valueOf(a));
		}
		ModuloInteger[] zeros = new ModuloInteger[adjacencyMatrix.length];
		Arrays.fill(zeros, ModuloInteger.ZERO);
		ZERO_CONFIG = DenseVector.valueOf(zeros);
		ModuloInteger[] ones = new ModuloInteger[adjacencyMatrix.length];
		Arrays.fill(ones, ModuloInteger.ONE);
		ONE_CONFIG = DenseVector.valueOf(ones);
		adjMatrix = DenseMatrix.valueOf(list);
		Set<DenseVector<ModuloInteger>> kernalsSet = new HashSet<DenseVector<ModuloInteger>>();
		int numOptions = (int) Math.pow(2, adjMatrix.getNumberOfRows());
		Set<DenseVector<ModuloInteger>> moveCombinations = new HashSet<DenseVector<ModuloInteger>>(numOptions, 0.0000001F);
		for(int i = 0; i < numOptions; ++i) {
			String shortBinaryValue = Integer.toBinaryString(i);
			StringBuilder binaryValue = new StringBuilder(adjMatrix.getNumberOfRows());
			while(binaryValue.length() < (adjMatrix.getNumberOfRows() - shortBinaryValue.length())) {
				binaryValue.append(0);
			}
			binaryValue.append(shortBinaryValue);
			String[] stringValues = binaryValue.toString().split("(?!^)"); //make an array of character strings
			ModuloInteger[] values = new ModuloInteger[adjMatrix.getNumberOfRows()];
			for(int j = 0; j < stringValues.length; ++j) {
				values[j] = ModuloInteger.valueOf(stringValues[j]);
			}
			DenseVector<ModuloInteger> moves = DenseVector.valueOf(values);
			moveCombinations.add(moves);
			if(getChangedVerticesAfterMoves(moves).equals(ZERO_CONFIG)) {
				kernalsSet.add(moves);
			}
		}
		moveCombs = moveCombinations;
		kernals = kernalsSet;
	}
	
	public DenseVector<ModuloInteger> getConfigAfterMoves(DenseVector<ModuloInteger> moves, DenseVector<ModuloInteger> configuration) {
		return getChangedVerticesAfterMoves(moves).plus(configuration);
	}
	
	public DenseVector<ModuloInteger> getChangedVerticesAfterMoves(DenseVector<ModuloInteger> moves) {
		return adjMatrix.transpose().times(moves);
	}
	
	public Set<DenseVector<ModuloInteger>> getKernals() {
		return kernals;
	}
	
	public DenseMatrix<ModuloInteger> getAdjacencyMatrix() {
		return adjMatrix;
	}
	
	public Set<DenseVector<ModuloInteger>> getAllMoveCombinations() {
		return moveCombs;
	}
	
	public Set<DenseVector<ModuloInteger>> getSolutions(DenseVector<ModuloInteger> start, DenseVector<ModuloInteger> end) {
		DenseVector<ModuloInteger> aSolution = null;
		for(DenseVector<ModuloInteger> moves : moveCombs) {
			if(end.equals(getConfigAfterMoves(moves, start))) {
				aSolution = moves;
				break;
			}
		}
		if(aSolution == null) {
			return new HashSet<DenseVector<ModuloInteger>>();
		}
		Set<DenseVector<ModuloInteger>> solutions = new HashSet<DenseVector<ModuloInteger>>(kernals.size(), 0.0000001F);
		for(DenseVector<ModuloInteger> kernal : kernals) {
			solutions.add(aSolution.plus(kernal));
		}
		return solutions;
	}
	
}
