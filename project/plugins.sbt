// format: off
val ScalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.3.1")

addSbtPlugin("ch.epfl.scala"        % "sbt-scalafix"                  % "0.9.24")
addSbtPlugin("com.dwijnand"         % "sbt-dynver"                    % "4.1.1")
addSbtPlugin("org.scalameta"        % "sbt-scalafmt"                  % "2.4.2")
addSbtPlugin("org.scalameta"        % "sbt-mdoc"                      % "2.2.13")
addSbtPlugin("org.jetbrains"        % "sbt-idea-plugin"               % "3.8.4")
addSbtPlugin("com.eed3si9n"         % "sbt-buildinfo"                 % "0.10.0")
