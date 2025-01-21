
import json
from anthropic import AnthropicVertex
import os
import time
from models import project_summary_db,code_profile_db
from flask import jsonify
import config

def get_validated_service_ai_response(system_prompt, user_content):
        max_retries = 3
        retry_count = 0
        
        while retry_count < max_retries:
            try:
                if not system_prompt:
                    print("System prompt is missing.")
                    return {"error_code": 400, "message": "System prompt is missing."}
                
                start_time = time.time()
                LOCATION = "us-east5"
                client = AnthropicVertex(region=LOCATION, project_id=config.LLM_PROJECT_ID)

                code_history=[]
                code_history.append({"role": "user", "content": user_content})
                
                # Add user new prompt chat to stored history
                try:
                    message = client.messages.create(
                        temperature=0,
                        top_k=0,
                        top_p=0,
                        max_tokens=4096,
                        system=system_prompt,
                        model="claude-3-5-sonnet@20240620",
                        messages=code_history
                    )
                except Exception as e:
                    print(f"Error communicating with AI service: {e}")
                    return {"error_code": 502, "message": "Failed to communicate with AI service."}

                ai_answer = message.content[-1].text
                # print(f"\nTime taken for query analysis request: {time.time() - start_time} s")
                # print("Response from Bypassing method: ", ai_answer)

                json_extracted = ai_answer
                json_code_start = "```json"
                json_code_end = "```"
                start_index = json_extracted.find(json_code_start) + len(json_code_start)
                end_index = json_extracted.find(json_code_end, start_index)

                if start_index != -1 and end_index != -1:
                    json_response = json_extracted[start_index:end_index].strip()
                    try:
                        ai_response_dict = json.loads(json_response)
                        final_response=ai_response_dict
                    except json.JSONDecodeError as e:
                        print(f"Error decoding AI response JSON: {e}")
                        return {"error_code": 500, "message": "Failed to decode AI response JSON."}
                    
                else:
                    final_response = {"error_code": 204, "message": "No content in AI response."}
                return final_response
            except Exception as e:
                retry_count += 1
                print("Retrying in 2 seconds...")
                time.sleep(2)
        return {"error_code": 500, "message": "Failed to generate AI response after retries."}


def get_service_names(system_prompt, user_prompt):
        max_retries = 3
        retry_count = 0
        while retry_count < max_retries:
            try:
                if not system_prompt:
                    return {"error_code": 400, "message": "System prompt is missing."}
                
                LOCATION = "us-east5"
                client = AnthropicVertex(region=LOCATION, project_id=config.LLM_PROJECT_ID)

                code_history=[]
                code_history.append({"role": "user", "content": user_prompt})
                
                try:
                    message = client.messages.create(
                        temperature=0,
                        top_k=0,
                        top_p=0,
                        max_tokens=4096,
                        system=system_prompt,
                        model="claude-3-haiku@20240307",
                        messages=code_history
                    )
                except Exception as e:
                    print(f"Error communicating with AI service: {e}")
                    return {"error_code": 502, "message": "Failed to communicate with AI service."}
                
                ai_answer =message.content[-1].text
                
                json_extracted = ai_answer
                json_code_start = "```json"
                json_code_end = "```"
                start_index = json_extracted.find("[")
                end_index = json_extracted.rfind("]")

                if start_index != -1 and end_index != -1:
                    json_response = json_extracted[start_index:end_index+1].strip()
                    try:
                        ai_response_dict = json.loads(json_response)
                        final_response={"ai_response": ai_response_dict}
                    except json.JSONDecodeError as e:
                        print(f"Error decoding AI response JSON: {e}")
                        return {"error_code": 500, "message": "Failed to decode AI response JSON."}
                    
                else:
                    final_response = {"error_code": 204, "message": "No content in AI response."}
                return jsonify(final_response)
            except Exception as e:
                retry_count += 1
                print("Retrying in 2 seconds...")
                time.sleep(2)
        return None
   
