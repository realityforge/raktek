require 'buildr/git_auto_version'
require 'buildr/gwt'

desc 'Experiments in WebGL'
define 'raktek' do
  project.group = 'org.realityforge.raktek'
  compile.options.source = '1.8'
  compile.options.target = '1.8'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  compile.with :javax_annotation,
               :jetbrains_annotations,
               :vecmath,
               :akasha,
               :grim_annotations,
               :jsinterop_base,
               :jsinterop_annotations,
               :braincheck,
               :gwt_user

  test.using :testng
  test.compile.with :mockito

  gwt(['raktek.Raktek'],
      {
        :java_args => %w(-Xms512M -Xmx1024M -Dgwt.watchFileChanges=false),
        :dependencies => project.compile.dependencies + [project.compile.target] + [Buildr.artifact(:gwt_user)],
        :gwtc_args => %w(-optimize 9 -checkAssertions -XmethodNameDisplayMode FULL -noincremental)
      }) unless ENV['GWT'] == 'no'
  ipr.add_gwt_configuration(project,
                            :name => 'Run Raktek',
                            :gwt_module => 'raktek.Raktek',
                            :start_javascript_debugger => false,
                            :open_in_browser => false,
                            :vm_parameters => '-Xmx2G -Djava.awt.headless=true -Dgwt.watchFileChanges=false',
                            :shell_parameters => "-strict -style PRETTY -XmethodNameDisplayMode FULL -nostartServer -incremental -codeServerPort 8889 -bindAddress 0.0.0.0 -deploy #{_(:generated, :gwt, 'deploy')} -extra #{_(:generated, :gwt, 'extra')} -war #{_(:generated, :gwt, 'war')}",
                            :launch_page => "http://127.0.0.1:8889/raktek/index.html")

  project.iml.add_gwt_facet({ 'raktek.Raktek' => false }, :settings => {
    :compilerMaxHeapSize => '1024',
    :compilerParameters => '-draftCompile -localWorkers 2 -strict'
  }, :gwt_dev_artifact => :gwt_dev)

  iml.excluded_directories << project._('tmp')

  ipr.add_component_from_artifact(:idea_codestyle)
  ipr.add_code_insight_settings
  ipr.add_nullable_manager
  ipr.add_javac_settings('-Xlint:all,-processing,-serial -Werror')
end
