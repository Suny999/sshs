package com.sshs.toolkit.coder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sshs.core.base.controller.BaseController;
import com.sshs.core.exception.BusinessException;
import com.sshs.core.message.Message;
import com.sshs.core.page.Page;
import com.sshs.core.util.ReflectHelper;
import com.sshs.core.util.Serializabler;
import com.sshs.toolkit.coder.helper.CoderGenerator;
import com.sshs.toolkit.coder.model.Coder;
import com.sshs.toolkit.coder.model.Column;
import com.sshs.toolkit.coder.service.ICoderService;

import reactor.core.publisher.Mono;

/**
 * 类名称： 代码生成器
 * 
 * @author Suny
 * @date 2017年10月23日
 * 
 * @version
 */
@RestController
@RequestMapping("/toolkit/coder")
public class CoderController extends BaseController {
	Log logger = LogFactory.getLog(CoderController.class);

	private final ICoderService coderService;

	@Autowired
	public CoderController(final ICoderService coderService) {
		this.coderService = coderService;
	}

	/**
	 * 獲取表列表
	 * 
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/tableList.do")
	public Mono<Page<Coder>> getTableList(@RequestBody Page<Coder> page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return Mono.justOrEmpty(
				coderService.findForPageList("com.sshs.toolkit.coder.dao.CoderDao.findDbTableForPageList", page));
	}

	/**
	 * 獲取字段列表
	 * 
	 * @param response
	 * @throws Exception
	 */
	@PostMapping("/colunmList.do")
	public Mono<Page<Column>> getColunmList(@RequestBody Page<Column> page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		coderService.findColumnForList(page);
		return Mono.justOrEmpty(page);
	}

	/**
	 * 生成代码
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/runCoder.do")
	public Mono<Message> runCoder(@RequestBody Coder coder, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			for (Column col : coder.getFields()) {
				if (StringUtils.isEmpty(coder.getTableName())) {
					coder.setTableName(col.getTableName());
					coder.setTableComment(col.getTableComment());
				}
				col.setPropertyName(ReflectHelper.getPropertyName(col.getColumnName()));
				col.setPropertyType(CoderGenerator.getPropertyType(col.getColumnType()));
			}

			coder.setColumns(Serializabler.object2Bytes(coder.getFields()));

			CoderGenerator.generate(coder);
			coderService.save(coder);
			return Mono.justOrEmpty(new Message("000000"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("-100001");
		}
	}
}