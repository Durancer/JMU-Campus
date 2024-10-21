package com.xueyu.common.core.result;

import com.xueyu.common.core.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

	/**
	 * 构建空列表分页返回
	 * @param current 当前页
	 * @param size 页大小
	 * @return 空列表
	 */
	public static ListVO buildNonDataRes(Integer current, Integer size){
		ListVO listVO = new ListVO<>();
		listVO.setRecords(new ArrayList<>());
		listVO.setTotal(0L);
		listVO.setPages(0L);
		listVO.setCurrent(current.longValue());
		listVO.setSize(size.longValue());
		return listVO;
	}

	/**
	 * 构建空列表分页返回
	 * @param request req
	 * @return 空列表
	 */
	public static ListVO buildNonDataRes(PageRequest request){
		ListVO listVO = new ListVO<>();
		listVO.setRecords(new ArrayList<>());
		listVO.setTotal(0L);
		listVO.setPages(0L);
		listVO.setCurrent(request.getCurrent().longValue());
		listVO.setSize(request.getSize().longValue());
		return listVO;
	}

}
