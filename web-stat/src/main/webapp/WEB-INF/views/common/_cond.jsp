<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
 <span>
     <i>${cond.label }：</i>
     <c:if test='${cond.condType == "NORMAL" }'>
      <input type="text" id="qry_${cond.name }" name="${cond.name }" placeholder="${currKVMap[cond.name] != null ? currKVMap[cond.name] : cond.defaultVal }" />
     </c:if>
           <c:if test='${cond.condType == "DATE" }'>
             <input type="text" id="qry_${cond.name }" name="${cond.name }" value="${currKVMap[cond.name] != null ? currKVMap[cond.name] : cond.defaultVal }" onclick="laydate()"/>
           </c:if>
           <c:if test='${cond.condType == "RADIO" }'>
             <c:forEach items="${cond.kvMap }" var="kv" varStatus="status">
               <p><input type="radio" id="qry_${cond.name }_${status.index}" name="${cond.name }" value="${kv.value }" ${(currKVMap[cond.name] != null && currKVMap[cond.name] == kv.value) || (cond.defaultVal != null && cond.defaultVal == kv.value) ? "checked" : "" }/><label>${kv.key }</label></p>
             </c:forEach>
           </c:if>
           <c:if test='${cond.condType == "CHECKBOX" }'>
             <c:forEach items="${cond.kvMap }" var="kv" varStatus="status">
               <p><input type="checkbox" id="qry_${cond.name }_${status.index}" name="${cond.name }" value="${kv.value }" 
               <c:choose>
                 <c:when test="${currKVMap[cond.name] != null }">
                   <c:forEach items="${currKVMap[cond.name] }" var="val">
                     <c:if test="${val == kv.value}">
                       checked
                     </c:if>
                   </c:forEach>
                 </c:when>
                 <c:when test="${(cond.defaultVal != null && cond.defaultVal == kv.value) }">
                    checked
                 </c:when>
               </c:choose>
               /><label>${kv.key }</label></p>
             </c:forEach>
           </c:if>
           <c:if test='${cond.condType == "SELECT" }'>
             <select id="qry_${cond.name }_${status.index}" name="${cond.name }">
               <c:forEach items="${cond.kvMap }" var="kv" varStatus="status">
                 <option value="${kv.value }" ${(currKVMap[cond.name] != null && currKVMap[cond.name] == kv.value) || (currKVMap[cond.name] == null && cond.defaultVal != null && cond.defaultVal == kv.value) ? "selected" : "" }>${kv.key }</option>
               </c:forEach>
             </select>
           </c:if>
         </span>
