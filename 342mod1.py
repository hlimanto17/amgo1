#!/usr/bin/env python

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import json, ast

class handler(BaseHTTPRequestHandler):
    def set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        self.set_headers()
        requested_name = self.raw_requestline.replace('GET ', '').split(' HTTP')[0][1:] #remove first slash

        try:
            #Retrieves first parameter, i.e., url:port/get or url:port/store
            store_or_get = requested_name.split('/')[0]
            #Retrieves user name, the second parameter, i.e., url:port/get/name or url:port/store/name:image_file
            name_of_user = requested_name.split('/')[1].lower()
        except IndexError:
            self.wfile.write('<html><body><h1>Query in this format "get/NAME" or "store/NAME:IMAGEFILE" </h1></body></html>\n')
            return

        if store_or_get.lower() == 'get':
            #If get, read file. 
            try: 
                location_file = open('image_file', 'r')
                data = location_file.read()
                location_file.close()

                #Retrieving all available images for the user.
                dictionary = "{\n\t'ImageFiles': {\n"
                dictionary += '\t\t\n'.join(data.lower().splitlines())
                dictionary += '\n\t}\n}'
                dictionary = ast.literal_eval(dictionary)

                try:
                    image_file = dictionary['ImageFiles'][name_of_user]
                    self.wfile.write('<html><body><h1>Image File for %s: %s</h1></body></html>\n' % (name_of_user, image_file))
                    send_response(200,image_file)
                    #a method to hurl the extracted image to the android app.
                    #do I need to specify the client?
                except KeyError:
                    self.wfile.write('<html><body><h1>The requested user [%s] was not found in the system.</h1></body></html>\n' % (name_of_user))
            except IOError:
                #if no file exists
                self.wfile.write('<html><body><h1>No image file is available.</h1></body></html>\n')
        
        elif store_or_get.lower() == 'store':
            #If store, retrieve the name and the image file from the second parameter
            (name_of_user, image_file) = name_of_user.split(':')
            #Write to file.
            with open('location_data', 'ab') as content:
                content.write(
                '''"%s": "%s"\n,''' % (name_of_user,image_file)
                )
            #Also send a response giving a confirmation.
            self.wfile.write('<html><body><h1>Stored image file [%s] for [%s] </h1></body></html>\n' % (image_file, name_of_user)) 
        else:
            #Query format error.
            self.wfile.write('<html><body><h1>Query in this format "get/NAME" or "store/NAME:IMAGEFILE" </h1></body></html>\n')
            return
        return

        
def run():
    http_serv = HTTPServer(('', 8080), handler)
    print 'Starting server'
    http_serv.serve_forever()

if __name__ == "__main__":
    run()
