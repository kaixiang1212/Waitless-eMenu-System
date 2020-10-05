from flask_wtf import FlaskForm
from flask_wtf.file import FileRequired, FileAllowed
from wtforms.validators import DataRequired
from wtforms import StringField, FloatField, SelectMultipleField, FileField, SubmitField


class New_Item_Form(FlaskForm):
    title = StringField('Title', validators=[DataRequired()])
    description = StringField('Description', validators=[DataRequired()])
    price = FloatField('Price', validators=[DataRequired()])
    category = SelectMultipleField('Category')
    image = FileField('Select Image',
                      validators=[FileRequired(),
                                  FileAllowed(['jpg', 'png', 'gif'], 'Only jpg, png and gif are allowed')])
    submit = SubmitField()


class New_Category_Form(FlaskForm):
    category_name = StringField('Category Name', validators=[DataRequired()])
    submit = SubmitField(_name='Add Category')
