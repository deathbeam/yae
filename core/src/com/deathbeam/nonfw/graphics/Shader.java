/*******************************************************************************
 * Copyright 2012 bmanuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.deathbeam.nonfw.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.deathbeam.nonfw.Utils;
import java.io.IOException;

public class Shader {
    public boolean pedantic = true;

    public ShaderProgram fromFile( String vertexFileName, String fragmentFileName ) throws IOException {
	return fromFile( vertexFileName, fragmentFileName, "" );
    }

    public ShaderProgram fromFile( String vertexFileName, String fragmentFileName, String defines ) throws IOException {
	Gdx.app.log( "ShaderLoader", "Compiling " + vertexFileName + " | " + fragmentFileName + "..." );
	String vertexShaderSrc = Utils.readFile(Utils.getResource(vertexFileName + ".vertex").read());
	String fragmentShaderSrc = Utils.readFile(Utils.getResource(fragmentFileName + ".fragment").read());
	return fromString( vertexShaderSrc, fragmentShaderSrc, vertexFileName, fragmentFileName, defines );
    }

    public ShaderProgram fromString( String vertex, String fragment, String vertexName, String fragmentName ) {
	return fromString( vertex, fragment, vertexName, fragmentName, "" );
    }

    public static ShaderProgram fromString( String vertex, String fragment, String vertexName, String fragmentName, String defines ) {
	ShaderProgram shader = new ShaderProgram( defines + "\n" + vertex, defines + "\n" + fragment );
	if( !shader.isCompiled() ) {
            Gdx.app.error( "ShaderLoader", shader.getLog() );
            Gdx.app.exit();
	} else {
            if( defines != null && defines.length() > 0 ) {
		Gdx.app.log( "ShaderLoader", vertexName + "/" + fragmentName + " compiled w/ (" + defines.replace( "\n", ", " )+ ")" );
            } else {
                Gdx.app.log( "ShaderLoader", vertexName + "/" + fragmentName + " compiled!" );
            }
	}

	return shader;
    }
}