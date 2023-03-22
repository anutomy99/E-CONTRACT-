from flask import*
app=Flask(__name__)
from src.database import*
import os
from werkzeug.utils import secure_filename
import functools
def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "lid" not in session:
            return redirect ("/")
        return func()
    return secure_function
app.secret_key="123"
@app.route('/logout')
def logout():
    session.clear()
    return render_template("login.html")

@app.route('/')
def start_page():
    return render_template('login.html')
@app.route('/login',methods=['post'])
def login():
    username=request.form['textfield']
    password=request.form['textfield2']
    qry="select*from login where username=%s and password=%s"
    val=(username,password)
    print(val,"========")
    res=selectone(qry,val)
    print("res",res)
    if res is None:
        return'''<script>alert("invalid");window.location="/"</script>'''
    elif res[3]=='admin':
        session['lid'] = res[0]
        return '''<script>alert("successfully login");window.location="adminhome"</script>'''
    elif res[3]=='contractor':
        session['lid']=res[0]
        return '''<script>alert("successfully login");window.location="contractrhome"</script>'''
    else:
        return'''<script>alert("invalid");window.location="/"</script>'''


@app.route('/adminhome')
@login_required
def adminhome():
    return render_template('adminhome.html')
@app.route('/approveuser')
@login_required

def approveuser():
    qry="SELECT `register`.*,`login`.`type` FROM `register` JOIN `login` ON `login`.`lid`=`register`.`lid` where login.type!='pending'"
    res=selectall(qry)
    print(res)
    return render_template('Approve users.html',val=res)
@app.route('/useraprove',methods=['post'])
@login_required

def useraprove():
    type = request.form['select']
    if type == "contractor":
        qry = "SELECT `register`.*,`login`.type FROM `register` JOIN `login` ON `register`.lid=`login`.lid WHERE TYPE='contractor'"
        res = selectall(qry)
        return render_template('job vaccancy.html', val=res, type=type)

    else:
        qry =" SELECT `register`.*,`login`.type FROM `register` JOIN `login` ON `register`.lid=`login`.lid WHERE TYPE='customer'"
        res = selectall(qry)
        return render_template('job vaccancy.html', val=res, type=type)

@app.route('/block')
@login_required

def block():

    id = request.args.get('lid')
    qry = "update login set type='blocked' where lid=%s"
    iud(qry, id)
    return '''<script>alert("block");window.location='/approveuser'</script>'''

@app.route('/unblock')
@login_required

def unblock():
    id = request.args.get('lid')
    qry = "update login set type='contractor' where lid=%s"
    iud(qry, id)
    return '''"<script>alert("unblock");window.location='/approveuser'</script>"'''


@app.route('/aprovecontractors')
@login_required

def aprovecontractors():
    qry="SELECT register.*,login.type FROM `register` JOIN `login` ON `login`.lid=`register`.lid where type='pending'"
    res=selectall(qry)
    return render_template('aprove contractors.html',val=res)

@app.route('/contractorregister')

def contractorregister():

    return render_template('contractor register.html')





@app.route('/contractorregister1',methods=['post'])
def contractorregister1():
    try:

        Firstname=request.form['textfield']
        Lastname=request.form['textfield2']
        Gender=request.form['radiobutton']
        Age=request.form['textfield3']
        Email=request.form['textfield4']
        Phone=request.form['textfield5']
        Place=request.form['textfield6']
        Post=request.form['textfield7']
        Pin=request.form['textfield8']
        UserName=request.form['textfield9']
        Password=request.form['textfield10']
        Service=request.form['select']

        latitude = request.form['textfield12']
        longitude = request.form['textfield13']

        qry="insert into login values(null,%s,%s,'pending')"
        val=(UserName,Password)
        lid=iud(qry,val)
        qry2="insert into register values(null,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        val2=(lid,Firstname,Lastname,Gender,Age,Place,Pin,Post,Service,Phone,Email)
        iud(qry2,val2)

        qry= "INSERT INTO `location` VALUES(NULL,%s,%s,%s)"
        val = (lid,latitude,longitude)
        iud(qry,val)

        return'''<script>alert("success");window.location='/'</script>'''
    except Exception as e:
        print(e)
        return '''<script>alert("username already exist");window.location='/'</script>'''


@app.route('/accept')

@login_required
def accept():
    id=request.args.get('lid')
    qry="update login set type='contractor' where lid=%s"
    iud(qry,id)
    return '''"<script>alert("accepted");window.location='/aprovecontractors'</script>"'''


@app.route('/reject')
@login_required

def reject():
    id=request.args.get('lid')
    qry="update login set type='rejected' where lid=%s"
    iud(qry,id)
    return '''"<script>alert("rejected");window.location='/aprovecontractors'</script>"'''


@app.route('/complaintandreply')
@login_required

def complaintandreply():
    qry="SELECT `complaint`.`complaint`,`date`,`complaint`.`reply`,`user`.`firstname`,complaint.complaint_id FROM `complaint` JOIN `user` WHERE`complaint`.`user_id`=`user`.lid"
    s=selectall(qry)
    return render_template('complaint and reply.html',val=s)



@app.route('/contractors')
@login_required

def contractors():
    return render_template('contractors.html')
@app.route('/jobvaccancy')
@login_required

def jobvaccancy():
    qry = "SELECT `vaccancy`.* FROM `vaccancy` JOIN `login` ON `vaccancy`.`contractor_id`=`login`.`lid` "
    res = selectall(qry)

    return render_template('job vaccancy.html',val=res)
# @app.route('/jovaccancy',methods=['post'])
# def jovaccancy():
#     type=request.form['select']
#     if type=="contractor":
#         qry="SELECT `vaccancy`.* FROM `vaccancy` JOIN `login` ON `vaccancy`.`contractor_id`=`login`.`lid` WHERE `login`.`type`='contractor'"
#         res=selectall(qry)
#         return render_template('job vaccancy.html',val=res,type=type)
#
#     else:
#         qry="SELECT `vaccancy`.* FROM `vaccancy` JOIN `login` ON `vaccancy`.`contractor_id`=`login`.`lid` WHERE `login`.`type`='customer'"
#         res=selectall(qry)
#         return render_template('job vaccancy.html',val=res,type=type)



@app.route('/reply')
@login_required

def reply():
    id=request.args.get('id')
    session['id']=id
    return render_template('reply.html')
@app.route('/cmpreply',methods=['post'])
@login_required

def cmpreply():
    id=session['id']
    reply=request.form['textarea']
    qry="update `complaint` SET  reply=%s WHERE `complaint_id`=%s"
    val=(reply,id)
    iud(qry,val)
    return'''<script>alert("successfully send reply");window.location="complaintandreply"</script>'''



@app.route('/viewcontractrequest')

@login_required
def viewcontractrequest():
    qry="SELECT `request`.*,`user`.`firstname`,user.`lastname`,`register`.`firstname`,register.`lastname` FROM `request` JOIN `user` ON `request`.`user_id`=`user`.`lid` JOIN `register` ON `register`.`lid`=`request`.`contractor_id`"
    res=selectall(qry)
    return render_template('view contract requst.html',val=res)
@app.route('/corequest',methods=['post'])
@login_required

def corequest():
     type=request.form['select']
     qry1 = "SELECT register.* FROM login JOIN register ON register.lid=login.lid WHERE login.type='contractor'"
     res1 = selectall(qry1)
     qry = "SELECT `user`.firstname,user.lastname,`request`.* FROM `user` JOIN `request` ON user.lid=request.user_id WHERE request.status='pending' and request.contractor_id=%s"
     res = selectall2(qry,type)
     return render_template('view contract requst.html', val1=res,val=res1,t=type)
#@app.route('/search')
# def search():
#     qry="select*from request"
#     res = selectall(qry)
  #  return render_template('view contract requst.html', val=res)

@app.route('/viewfeedback')
@login_required

def viewfeedback():

    return render_template('view feedback.html')

@app.route('/addwork',methods=['post'])
@login_required

def addwork():
    return render_template('add work.html')
@app.route('/workadd',methods=['post'])
@login_required

def workadd():
    work=request.form['textfield']
    amount=request.form['textfield2']
    Document=request.files['file2']
    fname=secure_filename(Document.filename)
    Document.save(os.path.join('static/upload',fname))
    qry="insert into work values(null,%s,%s,%s,%s)"
    val=(session['lid'],work,fname,amount)
    iud(qry,val)
    return '''<script>alert("success");window.location='manageworks#about'</script>'''


@app.route('/applicationuser')
@login_required

def applicationuser():
    return render_template('application user.html')
@app.route('/communication')
@login_required

def communication():
    qry="select*from user"
    res=selectall(qry)
    return render_template('communiction.html',val=res)



@app.route('/chatsp')
@login_required

def chatsp():

    return render_template('chatsp.html')



@app.route('/managefeatures')

@login_required
def managefeatures():
    qry="select*from features where `contractor_id`=%s"
    res=selectall2(qry,session['lid'])
    return render_template('manage features.html',val=res)


@app.route('/features1',methods=['post'])
@login_required

def features1():
    skills = request.form['textfield']
    experiance = request.form['textfield2']
    qry = "insert into features values(null,%s,%s,%s)"
    val = (session['lid'], skills, experiance)
    iud(qry, val)
    return '''<script>alert("success");window.location='managefeatures'</script>'''

@app.route('/features',methods=['post'])
@login_required

def features():
    return render_template('features.html')
@app.route('/managejobrequest')
@login_required

def managejobrequest():
    qry="SELECT `user`.`firstname`,`user`.`lastname`,`request`.`work`,`request`.`date`,request.status ,request.`contractor_id`,request.`request_id`FROM `user`JOIN `request`ON `user`.`lid`=`request`.`user_id` and request.status='pending' and request.contractor_id='"+str(session['lid'])+"'"
    res=selectall(qry)
    return render_template('manage job request.html',val=res)

@app.route('/acceptjob')
@login_required

def acceptjob():
    id=request.args.get('lid')
    qry="update request set status='accepted' where `request_id`=%s"
    iud(qry,id)
    return '''<script>alert("accepted");window.location='/managejobrequest'</script>'''
@app.route('/rejectjob')
@login_required

def rejectjob():
    id=request.args.get('lid')
    qry="update request set status='rejected' where `request_id`=%s"
    iud(qry,id)
    return '''"<script>alert("rejected");window.location='/managejobrequest'</script>"'''

@app.route('/acceptedwork')
@login_required

def acceptedwork():
    qry="SELECT `user`.`firstname`,`user`.`lastname`,`request`.`work`,`request`.`date`,`request_id`, `request`.`status` FROM `user`JOIN `request`ON `user`.`lid`=`request`.`user_id`  WHERE (`status`='accepted' or `status`='started' or `status`='ongoing') and request.contractor_id='"+str(session['lid'])+"'"
    res=selectall(qry)
    return render_template('accepted work.html',val=res)









@app.route('/manageworks')
@login_required

def manageworks():
    q="select * from work where `contractor_id`=%s"
    res=selectall2(q,session['lid'])
    return render_template('manage works.html',val=res)


@app.route('/productdetails',methods=['post'])
@login_required
def productdetails():
    return render_template('product details.html')

@app.route('/productdtls',methods=['post'])
@login_required
def productdtls():
    product=request.form['textfield']
    Qty=request.form['textfield2']
    Price=request.form['textfield3']
    image=request.files['file']
    file=secure_filename(image.filename)
    image.save(os.path.join('static/product',file))
    qry="insert into product values(null,%s,%s,%s,%s)"
    val = (product,Qty,Price,session['lid'])
    id=iud(qry,val)
    qry="INSERT INTO `product_img` VALUES(%s,%s)"
    val=(id,file)
    iud(qry,val)
    return '''<script>alert("success");window.location='product'</script>'''
@app.route('/delete')
@login_required

def delete():
    id=request.args.get('id')
    qry="delete from product where product_id=%s"
    iud(qry,str(id))
    qry = "delete from product_img where pid=%s"
    iud(qry, str(id))
    return '''<script>alert("succesfully deleted");window.location='product'</script>'''





@app.route('/delt')
@login_required

def delt():
    id=request.args.get('id')
    qry="delete from work where id=%s"
    iud(qry,str(id))
    return '''<script>alert("succesfully deleted");window.location='manageworks'</script>'''

@app.route('/deltf')
@login_required

def deltf():
    id=request.args.get('id')
    qry="delete from features where features_id=%s"
    iud(qry,str(id))
    return '''<script>alert("succesfully deleted");window.location='managefeatures'</script>'''








@app.route('/productlist')
@login_required
def productlist():
    id = request.args.get('id')
    qry="SELECT product_name,orderdproduct.quantity,orderdproduct.price FROM `order` JOIN `user` ON `user`.`lid`=`order`.`user_id` join orderdproduct on orderdproduct.order_id=order.order_id join  product on orderdproduct.product_id=product.product_id where order.order_id='"+id+"'"
    res=selectall(qry)
    return render_template('product list.html',val=res)


@app.route('/product')
@login_required
def product():
    qry="SELECT `product`.*,`product_img`.* FROM `product` JOIN `product_img` ON `product`.`product_id`=`product_img`.pid and contractor_id='"+str(session['lid'])+"'"
    res=selectall(qry)
    return render_template('product.html',val=res)




@app.route('/sendfeedback',methods=['post'])
@login_required

def sendfeedback():
    feedback=request.form['textfield']
    qry="insert into feedback values(null,%s,%s,curdate(),'contractor')"
    val=(feedback,session['lid'])
    iud(qry,val)
    return'''<script>alert("send feedback");window.location='contractrhome'</script>'''
@app.route('/sdfeedback')
@login_required

def sdfeedback():

    return render_template('send feedback.html')



@app.route('/feedback',methods=['post'])
@login_required

def feedback():
    type=request.form['select']
    if type=="contractor":
        qry="SELECT `feedback`.*,`register`.`firstname`,`lastname` FROM `register` JOIN `feedback` ON `feedback`.`user_id`=`register`.lid"
        res=selectall(qry)
    else:
        qry = "SELECT `feedback`.*,`user`.`firstname`,`lastname` FROM `user` JOIN `feedback` ON `feedback`.`user_id`=`user`.lid"
        res = selectall(qry)
    return render_template('view feedback.html',val=res,type=type)

@app.route('/update_work')
@login_required

def update_work():
    id= request.args.get('id')
    session['wid']=id
    qry="select * from request where request_id=%s"
    val=(id)
    res=selectone(qry, val)
    return render_template('update status.html', data=res)


@app.route('/updatestatus',methods=['post'])
@login_required

def updatestatus():
    status= request.form['select']
    qry = "update request set status=%s where `request_id`=%s"
    val=(status, session['wid'])
    iud(qry,val)
    return'''<script>alert("update succesfully");window.location='acceptedwork'</script>'''

@app.route('/vaccancy')
@login_required

def vaccancy():
    qry="select * from vaccancy where `contractor_id`=%s"
    res=selectall2(qry,session['lid'])
    return render_template('vaccancy.html',val=res)


@app.route('/addvaccancy',methods=['post'])
@login_required

def addvaccancy():
    import datetime
    return render_template('add vaccancy.html',d=datetime.datetime.now().strftime("%Y-%m-%d"))

@app.route('/addvacc',methods=['post'])
@login_required

def addvacc():
    Job=request.form['textfield']
    Description=request.form['textfield2']
    date=request.form['textfield3']
    qry="insert into vaccancy values(null,%s,%s,%s,%s)"
    val=(Job,Description,session['lid'],date)
    iud(qry,val)
    return'''<script>alert("success");window.location='vaccancy#about'</script>'''



@app.route('/editvaccancy')
@login_required

def editvaccancy():
    id=request.args.get('id')
    session['vid']=id
    qry="select*from vaccancy  where vaccancy_id=%s "
    res=selectone(qry,str(id))
    print(res)
    return render_template('updatevaccancy.html',val=res)



@app.route('/editv',methods=['post'])
@login_required

def editv():
    job=request.form['textfield']
    Description=request.form['textfield22']
    date=request.form['textfield2']
    qry="UPDATE `vaccancy`SET `job`=%s,`details`=%s,`date`=%s WHERE `vaccancy_id`=%s"
    val=(job,Description,date,str(session['vid']))
    iud(qry,val)
    return'''<script>alert("updated successfully");window.location='vaccancy#about'</script>'''


@app.route('/deletev')
@login_required
def deletev():
    id=request.args.get('id')
    qry="delete from vaccancy where vaccancy_id=%s "
    iud(qry,str(id))
    return'''<script>alert("successfully deleted");window.location='vaccancy#about'</script>'''



@app.route('/pend')
@login_required
def pend():
    id=request.args.get('id')
    qry="select user.*,`job application`.id from `job application`,vaccancy,user where `job application`.vaccancy_id='"+id+"' and user.lid=`job application`.user_id and `job application`.vaccancy_id=vaccancy.vaccancy_id and contractor_id='"+str(session['lid'])+"' and `job application`.status='pending'"
    q=selectall(qry)
    return render_template("pending.html",val=q)

@app.route('/apend')
@login_required
def apend():
    id = request.args.get('id')
    qry = "select user.*,`job application`.id from `job application`,vaccancy,user where user.lid=`job application`.user_id and `job application`.vaccancy_id=vaccancy.vaccancy_id and contractor_id='" + str(
        session['lid']) + "' and `job application`.status='Approved' and `job application`.vaccancy_id='"+id+"'"
    q = selectall(qry)
    return render_template("apending.html",val=q)


@app.route('/appr')
@login_required
def appr():
    id = request.args.get('id')
    qry = "update `job application` set status='Approved' where id='"+id+"'"
    iud2(qry)
    return '''<script>alert("Approved");window.location='vaccancy#about'</script>'''


@app.route('/rjct')
@login_required
def rjct():
    id = request.args.get('id')
    qry = "update `job application` set status='Rejected' where id='"+id+"'"
    iud2(qry)
    return '''<script>alert("Rejected");window.location='vaccancy#about'</script>'''


@app.route('/vieworder')
@login_required

def vieworder():
    qry="SELECT `order`.*,`user`.`firstname`,`user`.`lastname` FROM `order` JOIN `user` ON `user`.`lid`=`order`.`user_id` join orderdproduct on orderdproduct.order_id=order.order_id join  product on orderdproduct.product_id=product.product_id where contractor_id='"+str(session['lid'])+"' group by order.order_id"
    s=selectall(qry)
    print(s)
    return render_template('view orders.html',val=s)


@app.route("/contractrhome")
@login_required

def contractrhome():
    return render_template("contractr home.html")





@app.route('/sendmsg2',methods=['post'])
@login_required

def sendmsg2():

    msg=request.form['textarea']
    print(msg)
    qry="INSERT INTO `chat` VALUES(NULL,%s,%s,%s,curdate())"
    val=(session['lid'],session['sid'],msg)
    iud(qry,val)
    qry = "select * from user where lid=%s"
    res = selectone(qry, session['sid'])
    qry = "SELECT * FROM `chat` WHERE `fromid`=%s AND `toid`=%s or `fromid`=%s AND `toid`=%s "
    val = (session['lid'], session['sid'], session['sid'], session['lid'])
    res1 = selectall2(qry, val)
    fname = res[2]
    print(fname)
    lname = res[3]

    return render_template('chatsp.html',data=res1,fname=fname,lname=lname,fr=session['sid'])




@app.route("/chatsp1")
@login_required

def chatsp1():
    sid=request.args.get('id')
    session['sid']=sid
    qry="select * from user where lid=%s"
    res=selectone(qry,sid)
    qry="SELECT * FROM `chat` WHERE `fromid`=%s AND `toid`=%s or `fromid`=%s AND `toid`=%s "
    val=(session['lid'],session['sid'],session['sid'],session['lid'])
    res1=selectall2(qry,val)
    fname=res[2]
    lname=res[3]
    return render_template("chatsp.html",data=res1,fname=fname,lname=lname,fr=session['sid'])







@app.route('/contractorschatgrp',methods=['GET','POST'])
@login_required

def contractorschatgrp():
    fid=session['lid']
    qry="SELECT `register`.`firstname`, `register`.`lastname`,`msg`,`date`,`gpchathod`.`id`,`gpchathod`.fid FROM `gpchathod` JOIN `register` ON `register`.`lid`=`gpchathod`.`fid`    ORDER BY `gpchathod`.`id` "
    s=selectall(qry)
    print(s)
    return render_template('gpchat.html',data=s,fname="",fr=session['lid'])



@app.route('/send1',methods=['GET','POST'])
@login_required

def send1():
    msg=request.form['textarea']
    qry="insert into gpchathod values(null,%s,%s,curdate()) "
    val=(str(session['lid']),msg)
    iud(qry,val)
    return redirect('/contractorschatgrp')



app.run(debug=True)

