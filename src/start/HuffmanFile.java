package start;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanFile {
	public static final int CODE_SIZE = 256;
	public static final int CACHE_SIZE = 1024;
	public static final int ZIP_HEAD = 0xff;

	public static void main(String[] args) throws IOException {
		String srcPath = "D://Pic.bmp";
		String compressPath = "D://test.txt";
		String deCompressPath = "D://t1.bmp";
		compress(srcPath, compressPath);
		deCompress(compressPath, deCompressPath);
	}

	//统计文件，返回文件长度
	public static int statisticsFile(File file, int[] byteCount)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		byte[] b = new byte[CACHE_SIZE];
		int readLen = CACHE_SIZE;
		int fileLen = 0;
		while (readLen == CACHE_SIZE) {
			readLen = bis.read(b, 0, b.length);
			for (int i = 0; i < readLen; i++) {
				byteCount[b[i] & 0xff]++;
			}
			fileLen += readLen;
		}
		bis.close();
		return fileLen;
	}

	//生成huffman树
	public static Node1[] generateHuffmanTree(int[] byteCount) {

		PriorityQueue<Node1> queue = new PriorityQueue<Node1>(new Comparator<Node1>() {
			@Override
			public int compare(Node1 o1, Node1 o2) {
				if (o1.val > o2.val)
					return 1;
				if (o1.val == o2.val)
					return 0;
				return -1;
			}
		});
		Node1[] nodes = new Node1[CODE_SIZE * 2];
		int i;
		for (i = 0; i < CODE_SIZE; i++) {
			nodes[i] = new Node1();
			nodes[i].lchild = nodes[i].rchild = -1;
			nodes[i].val = byteCount[i];
			nodes[i].nodeName = (short) i;
			queue.add(nodes[i]);
		}
		while (queue.size() > 1) {
			Node1 n1 = queue.poll();
			Node1 n2 = queue.poll();
			Node1 n3 = new Node1();
			n3.lchild = n1.nodeName;
			n3.rchild = n2.nodeName;
			n3.val = n1.val + n2.val;
			n3.nodeName = (short) i;
			nodes[i++] = n3;
			n1.parent = n3.nodeName;
			n2.parent = n3.nodeName;
			queue.add(n3);
		}
		return nodes;
	}


	public static void Code(Node1[] nodes, int curNode, String curCode,
			String[] codes) {
		if (curNode < CODE_SIZE) {
			codes[curNode] = curCode;
			return;
		}
		Code(nodes, nodes[curNode].lchild, curCode + "0", codes);
		Code(nodes, nodes[curNode].rchild, curCode + "1", codes);
	}


	public static String[] generateHuffmanCode(Node1[] nodes) {
		String[] codes = new String[CODE_SIZE];
		Code(nodes, 510, "", codes);
		return codes;
	}

	public static byte binary2byte(StringBuilder str, int start) {
		int b = 0;
		if (str.charAt(start) == '0')
			b = b | 0x0;
		else
			b = b | 0x1;
		for (int i = 1; i < 8; i++) {
			b = b << 1;
			if (str.charAt(start + i) == '0')
				b = b | 0x0;
			else
				b = b | 0x1;
		}
		return (byte) b;

	}


	public static void writeCompressFile(File srcFile, File tarFile,
			String[] codes, int srcFileLen, Node1[] nodes) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				srcFile));
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(tarFile));
		// 写文件头标志
		bos.write(ZIP_HEAD);

		bos.write(srcFile.getName().getBytes().length);

		bos.write(srcFile.getName().getBytes());

		//写文件长度
		int len1 = srcFileLen >> 24;
		bos.write(len1);
		int len2 = (srcFileLen - (len1 << 24)) >> 16;
		bos.write(len2);
		int len3 = (srcFileLen - (len1 << 24) - (len2 << 16)) >> 8;
		bos.write(len3);
		int len4 = srcFileLen - (len1 << 24) - (len2 << 16) - (len3 << 8);
		bos.write(len4);

		for (int i = CODE_SIZE; i < 2 * CODE_SIZE - 1; i++) {

			bos.write(nodes[i].lchild >> 8);

			bos.write(nodes[i].lchild % 256);

			bos.write(nodes[i].rchild >> 8);

			bos.write(nodes[i].rchild % 256);
		}

		byte[] buffer = new byte[CACHE_SIZE];

		StringBuilder sb = new StringBuilder();

		int readLen = CACHE_SIZE;

		String rest = "";

		while (readLen == CACHE_SIZE) {

			readLen = bis.read(buffer, 0, CACHE_SIZE);

			sb.delete(0, sb.length());

			sb.append(rest);

			for (int i = 0; i < readLen; i++) {
				sb.append(codes[buffer[i] & 0xff]);
			}
			int cur = 0;

			while ((cur + 1) * 8 <= sb.length() && cur < CACHE_SIZE) {

				buffer[cur] = binary2byte(sb, cur * 8);
				cur++;
			}

			bos.write(buffer, 0, cur);

			rest = sb.substring(cur * 8);
		}

		if (rest.length() > 0) {
			sb.delete(0, sb.length());
			sb.append(rest);

			while (sb.length() < 8)
				sb.append("0");
			byte b = binary2byte(sb, 0);

			bos.write(b);
		}
		bis.close();
		bos.close();
	}


	public static boolean compress(String srcPath, String tarPath)
			throws IOException {
		if (srcPath == null || tarPath == null)
			return false;
		File srcFile = new File(srcPath);
		File tarFile = new File(tarPath);
		int[] byteCount = new int[CODE_SIZE];
		System.out.println("???......");
		int srcFileLen = statisticsFile(srcFile, byteCount);

		Node1[] nodes = generateHuffmanTree(byteCount);
		String[] codes = generateHuffmanCode(nodes);
		long start = System.currentTimeMillis();
		writeCompressFile(srcFile, tarFile, codes, srcFileLen, nodes);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		return true;
	}


	public static boolean deCompress(String srcPath, String tarPath)
			throws IOException {
		if (srcPath == null || tarPath == null)
			return false;
		File srcFile = new File(srcPath);
		File tarFile = new File(tarPath);
		System.out.println(".....");
		long start = System.currentTimeMillis();
		writeDeCompressFile(srcFile, tarFile);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		return true;
	}

	public static boolean writeDeCompressFile(File srcFile, File tarFile)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				srcFile));
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(tarFile));

		if (bis.read() != 255) {
			bis.close();
			bos.close();
			return false;
		}

		int deCompressFileNameLen = bis.read();

		byte[] deCompressFileNameBytes = new byte[deCompressFileNameLen];
		bis.read(deCompressFileNameBytes, 0, deCompressFileNameBytes.length);


		byte[] deCompressFileLenBytes = new byte[4];
		bis.read(deCompressFileLenBytes, 0, 4);
		int len1 = (deCompressFileLenBytes[0] & 0xff) << 24;
		int len2 = (deCompressFileLenBytes[1] & 0xff) << 16;
		int len3 = (deCompressFileLenBytes[2] & 0xff) << 8;
		int len4 = deCompressFileLenBytes[3] & 0xff;
		int deCompressFileLen = len1 + len2 + len3 + len4;

		Node1[] nodes = new Node1[CODE_SIZE - 1];
		byte[] nodesBytes = new byte[4 * (CODE_SIZE - 1)];

		bis.read(nodesBytes, 0, nodesBytes.length);
		for (int i = 0; i < CODE_SIZE - 1; i++) {
			nodes[i] = new Node1();
			nodes[i].lchild = (short) ((nodesBytes[i * 4 + 1] & 0xff) + (nodesBytes[i * 4] * 256));
			nodes[i].rchild = (short) ((nodesBytes[i * 4 + 3] & 0xff) + (nodesBytes[i * 4 + 2] * 256));
		}


		byte[] readBuffer = new byte[CACHE_SIZE];
		byte[] writeBuffer = new byte[CACHE_SIZE];
		int readLen = CACHE_SIZE;
		int writeLen = 0;
		int writeSum = 0;
		int curNode = CODE_SIZE * 2 - 2;

		loop1: while (readLen == CACHE_SIZE) {
			readLen = bis.read(readBuffer, 0, CACHE_SIZE);

			for (int i = 0; i < readLen; i++) {

				for (int j = 7; j >= 0; j--) {
					if (((readBuffer[i] >> j) & 0x1) == 0) {
						curNode = nodes[curNode - CODE_SIZE].lchild;
					} else {
						curNode = nodes[curNode - CODE_SIZE].rchild;
					}

					if (curNode < CODE_SIZE) {

						writeBuffer[writeLen++] = (byte) curNode;

						if (writeLen == CACHE_SIZE) {

							if (writeSum + writeLen > deCompressFileLen)
								break loop1;
							bos.write(writeBuffer);
							writeSum += writeLen;
							writeLen = 0;
						}
						curNode = CODE_SIZE * 2 - 2;
					}
				}
			}
		}
		if (writeLen > 0) {
			System.out.println(writeLen + " " + writeSum + " "
					+ deCompressFileLen);

			bos.write(writeBuffer, 0, deCompressFileLen - writeSum);
		}
		bis.close();
		bos.close();
		return true;
	}

}

class Node1 {
	short nodeName;// ????????
	int val;// ????????
	short lchild;// ??????
	short rchild;// ??????
	short parent;// ??????
}