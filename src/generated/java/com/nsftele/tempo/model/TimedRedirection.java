/**
 * Tempo Net API
 * Tempo Net API provides HTTP-based API for controlling Tempo Platform.   <table> <style type=\"text/css\" scoped> th, tr, td  { border: 0px; }</style> <thead>     <tr>             <th colspan=\"7\">Interface hierarchy</th>     </tr> </thead> <body> <tr>     <td>project</td>     <td>+</td>     <td>template</td> </tr> <tr>     <td></td>     <td>|</td>     <td></td> </tr> <tr>     <td></td>     <td>+</td>     <td>company</td>     <td>-</td>     <td>short number</td>     <td>-</td>     <td>call tracking</td> </tr> </tbody> </table> 
 *
 * OpenAPI spec version: 1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.nsftele.tempo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * TimedRedirection
 */

public class TimedRedirection   {
  @JsonProperty("end_date")
  private String endDate = null;

  @JsonProperty("end_time")
  private String endTime = null;

  @JsonProperty("start_date")
  private String startDate = null;

  @JsonProperty("start_time")
  private String startTime = null;

  @JsonProperty("weekday_mask")
  private Integer weekdayMask = null;

  public TimedRedirection endDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

   /**
   * this redirection expires after this date. Format is YYYY-MM-DD
   * @return endDate
  **/
  @ApiModelProperty(example = "null", value = "this redirection expires after this date. Format is YYYY-MM-DD")
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public TimedRedirection endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * this redirection is applicable before this time of the day. Format is HH:MM:SS
   * @return endTime
  **/
  @ApiModelProperty(example = "null", value = "this redirection is applicable before this time of the day. Format is HH:MM:SS")
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public TimedRedirection startDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

   /**
   * this redirection is applicable on and after this date. Format is YYYY-MM-DD
   * @return startDate
  **/
  @ApiModelProperty(example = "null", value = "this redirection is applicable on and after this date. Format is YYYY-MM-DD")
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public TimedRedirection startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * this redirection is applicable at and after this time of the day. Format is HH:MM:SS
   * @return startTime
  **/
  @ApiModelProperty(example = "null", value = "this redirection is applicable at and after this time of the day. Format is HH:MM:SS")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public TimedRedirection weekdayMask(Integer weekdayMask) {
    this.weekdayMask = weekdayMask;
    return this;
  }

   /**
   * applicable week days. This value is a bitmask converted to decimal number. Mask bits are sat, fri, thu, wed, tue, mon, sun. So if you want the mask to match on fridays and thursdays you would convert 0110000 to a decimal number which is 48. 
   * @return weekdayMask
  **/
  @ApiModelProperty(example = "null", value = "applicable week days. This value is a bitmask converted to decimal number. Mask bits are sat, fri, thu, wed, tue, mon, sun. So if you want the mask to match on fridays and thursdays you would convert 0110000 to a decimal number which is 48. ")
  public Integer getWeekdayMask() {
    return weekdayMask;
  }

  public void setWeekdayMask(Integer weekdayMask) {
    this.weekdayMask = weekdayMask;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimedRedirection timedRedirection = (TimedRedirection) o;
    return Objects.equals(this.endDate, timedRedirection.endDate) &&
        Objects.equals(this.endTime, timedRedirection.endTime) &&
        Objects.equals(this.startDate, timedRedirection.startDate) &&
        Objects.equals(this.startTime, timedRedirection.startTime) &&
        Objects.equals(this.weekdayMask, timedRedirection.weekdayMask);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endDate, endTime, startDate, startTime, weekdayMask);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimedRedirection {\n");
    
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    weekdayMask: ").append(toIndentedString(weekdayMask)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

