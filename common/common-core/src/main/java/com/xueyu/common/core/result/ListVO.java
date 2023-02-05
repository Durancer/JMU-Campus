package com.xueyu.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页显示实体类
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListVO<T> {

	/**
	 * 数据信息
	 */
	List<T> records;

	/**
	 * 总数
	 */
	Long total;

	/**
	 * 每页显示条数
	 */
	Long size;

	/**
	 * 页数
	 */
	Long pages;

	/**
	 * 当前页
	 */
	Long current;

}
