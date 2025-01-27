import pycodestyle

def check_pep8_compliance(script_code):
    # print("Inside Checker")
    style = pycodestyle.StyleGuide(quiet=True)
    report = pycodestyle.Checker("<string>",script_code)

    report.check_all()
    errors = list(report.report._deferred_print)

    if not errors:
        # print(f"The Code follows PEP 8 style guidelines.")
        msg = ['The Code Follows EP 8 style guidelines.']
        return False,msg
    else:
        msg = [f'The Code has {len(errors)} PEP 8 violations:']
        # print(f"The Code has {len(errors)} PEP 8 violations:")
        # for error in errors:
        #     print(error)
        #     msg.append(error)
        
        return True,msg

# if __name__ == "__main__":
#     # file_path = "file.py"
#     file_path = """
# \ndef ROUND(AMT, PWR, SWT%):\n  return int((AMT + sgn(amt) * .00000001 + SGN(AMT) * SWT% * 5 / (PWR * 10)) * PWR) / PWR\n
# """
#     check_pep8_compliance(file_path)
